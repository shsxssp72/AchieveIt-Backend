package com.april.achieveit_project.service;

import com.april.achieveit_common.utility.SnowFlakeIdGenerator;
import com.april.achieveit_project.entity.ProjectFunction;
import com.april.achieveit_project.mapper.ProjectFunctionMapper;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        List<ProjectFunction> extractedFunctions=matchFunctionsToExistingOnes(projectId,functions);
        projectFunctionMapper.deleteByProjectId(projectId);
        extractedFunctions.forEach(i->projectFunctionMapper.insert(i));
    }

    public Long getNewId()
    {
        return snowFlakeIdGenerator.getNextId();
    }

    public List<ProjectFunction> GetAllProjectFunctions(String projectId)
    {
        return projectFunctionMapper.selectByProjectId(projectId);
    }

    @SneakyThrows
    public String GetFunctionCsv(String projectId)
    {
        List<ProjectFunction> functions=GetAllProjectFunctions(projectId);
        StringBuilder csvBuilder=new StringBuilder();

        CSVPrinter printer=new CSVPrinter(csvBuilder,
                                          CSVFormat.DEFAULT.withHeader(CsvHeaders));
        for(ProjectFunction item: functions)
        {
            printer.printRecord(item.getFunctionId(),
                                item.getSuperiorFunctionId(),
                                item.getDescription());
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
        records.forEach(i->{
            functions.add(new HashMap<>(){{
                put("id_for_display",i.get("function_id"));
                put("superior_function_id",i.get("superior_function_id"));
                put("function_description",i.get("function_description"));
            }});
        });
        return matchFunctionsToExistingOnes(projectId,functions);
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
