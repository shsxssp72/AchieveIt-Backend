package com.april.achieveit_project.service;

import com.april.achieveit_common.utility.SnowFlakeIdGenerator;
import com.april.achieveit_project.entity.ProjectFunction;
import com.april.achieveit_project.mapper.ProjectFunctionMapper;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectFunctionService
{
    private static Logger logger=LoggerFactory.getLogger(ProjectFunctionService.class);
    @Autowired
    ProjectFunctionMapper projectFunctionMapper;

    @Value("${snowflake.datacenter-id}")
    private Long datacenterId;
    @Value("${snowflake.machine-id}")
    private Long machineId;
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    private static final String[] CsvHeaders={"function_id","superior_function_id","function_description"};

    @PostConstruct
    private void init()
    {
        snowFlakeIdGenerator=new SnowFlakeIdGenerator(datacenterId,
                                                      machineId);
    }

    public void UpdateFunctions(String projectId,List<Map<String,String>> functions)//frontend pass no-function-id function, use displayId to verify
    {
        List<ProjectFunction> extractedFunctions=matchFunctionsToExistingOnes(projectId,
                                                                              functions);
        projectFunctionMapper.deleteByProjectId(projectId);
        extractedFunctions.forEach(i->projectFunctionMapper.insert(i));
    }

    public Long getNewId()
    {
        return snowFlakeIdGenerator.getNextId();
    }

    public ImmutablePair<List<Map<String,String>>,List<Map<String,String>>> ClassifyFunctionByIsSuperior(String projectId)
    {
        List<ProjectFunction> projectFunctions=projectFunctionMapper.selectByProjectId(projectId);
        Map<Long,String> displayIdMap=projectFunctions.parallelStream()
                .collect(Collectors.toMap(ProjectFunction::getFunctionId,
                                          ProjectFunction::getIdForDisplay));
        List<Map<String,String>> superiors=new LinkedList<>();
        List<Map<String,String>> inferiors=new LinkedList<>();
        for(var item: projectFunctions)
        {
            if(item.getSuperiorFunctionId()
                    .equals(item.getFunctionId()))
            {
                superiors.add(Map.of("display_id",
                                     item.getIdForDisplay(),
                                     "function_description",
                                     item.getDescription(),
                                     "superior_display_id",
                                     item.getIdForDisplay()));
            }
            else
            {
                inferiors.add(Map.of("display_id",
                                     item.getIdForDisplay(),
                                     "function_description",
                                     item.getDescription(),
                                     "superior_display_id",
                                     displayIdMap.get(item.getSuperiorFunctionId())));
            }
        }
        superiors.sort(Comparator.comparing(i->i.get("display_id")));
        inferiors.sort(Comparator.comparing(i->i.get("display_id")));
        return new ImmutablePair<>(superiors,inferiors);
    }

    public List<Map<String,String>> GetAllProjectFunctions(String projectId)
    {
        ImmutablePair<List<Map<String,String>>,List<Map<String,String>>> classifiedResult=ClassifyFunctionByIsSuperior(projectId);
        List<Map<String,String>> superiors=classifiedResult.left;
        List<Map<String,String>> inferiors=classifiedResult.right;

        superiors.addAll(inferiors);
        return superiors;
    }

    @SneakyThrows
    public String GetFunctionCsv(String projectId)
    {
        List<Map<String,String>> functions=GetAllProjectFunctions(projectId);
        StringBuilder csvBuilder=new StringBuilder();

        CSVPrinter printer=new CSVPrinter(csvBuilder,
                                          CSVFormat.DEFAULT.withHeader(CsvHeaders));

        for(Map<String,String> item: functions)
        {
            printer.printRecord(item.get("function_id"),
                                item.get("superior_function_id"),
                                item.get("description"));
        }
        return csvBuilder.toString();
    }

    @SneakyThrows
    public List<ProjectFunction> ParseFunctionCsv(String projectId,String csvContent)
    {

        Iterable<CSVRecord> records=CSVFormat.DEFAULT.withHeader(CsvHeaders)
                .withFirstRecordAsHeader()
                .parse(new StringReader(csvContent));

        List<Map<String,String>> functions=new LinkedList<>();
        records.forEach(i->
                        {
                            functions.add(new HashMap<>()
                            {{
                                put("id_for_display",
                                    i.get("function_id"));
                                put("superior_function_id",
                                    i.get("superior_function_id"));
                                put("function_description",
                                    i.get("function_description"));
                            }});
                        });
        return matchFunctionsToExistingOnes(projectId,
                                            functions);
    }

    private List<ProjectFunction> matchFunctionsToExistingOnes(String projectId,List<Map<String,String>> functions)
    {
        Map<String,ProjectFunction> existingFunctions=projectFunctionMapper.selectByProjectId(projectId)
                .parallelStream()
                .collect(Collectors.toMap(ProjectFunction::getIdForDisplay,
                                          i->i));
        List<ProjectFunction> extractedFunctions=new LinkedList<>();
        Map<String,Long> submittedDisplayIdMap=new HashMap<String,Long>();

        for(Map<String,String> item: functions)
        {
            String idForDisplay=item.get("id_for_display");
            String superiorFunctionId=item.get("superior_function_id");//DisplayId version
            String functionDescription=item.get("function_description");

            ProjectFunction function=new ProjectFunction();

            if(existingFunctions.containsKey(idForDisplay))
            {
                function.setFunctionId(existingFunctions.get(idForDisplay)
                                               .getFunctionId());
            }
            else
            {
                function.setFunctionId(getNewId());
            }
            submittedDisplayIdMap.put(function.getIdForDisplay(),
                                      function.getFunctionId());
            function.setSuperiorFunctionId(submittedDisplayIdMap.get(superiorFunctionId));
            function.setDescription(functionDescription);
            function.setReferredProjectId(projectId);

            extractedFunctions.add(function);
        }
        return extractedFunctions;
    }
}