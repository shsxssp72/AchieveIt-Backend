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
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public void UpdateFunctions(String projectId,List<Map<String,String>> functions)
    {
        List<ProjectFunction> extractedFunctions=new LinkedList<>();
        for(Map<String,String> item: functions)
        {
            ProjectFunction function=new ProjectFunction(Long.parseLong(item.get("function_id")),
                                                         projectId,
                                                         Long.parseLong(item.get("superior_function_id")),
                                                         item.get("function_description"));
            extractedFunctions.add(function);
        }
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

    public List<ProjectFunction> ParseFunctionCsv(String csvContent) throws IOException
    {

        Iterable<CSVRecord> records=CSVFormat.DEFAULT.withHeader(CsvHeaders)
                .withFirstRecordAsHeader()
                .parse(new StringReader(csvContent));
        List<ProjectFunction> extractedFunctions=new LinkedList<>();
        for(CSVRecord item:records)
        {
            ProjectFunction function=new ProjectFunction(Long.parseLong(item.get("function_id")),
                                                         item.get("referred_project_id"),
                                                         Long.parseLong(item.get("superior_function_id")),
                                                         item.get("function_description"));
            extractedFunctions.add(function);
        }
        return extractedFunctions;
    }

}
