package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_project.service.ProjectFunctionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/")
public class ProjectFunctionController
{
    private static Logger logger=LoggerFactory.getLogger(ProjectFunctionController.class);
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProjectFunctionService projectFunctionService;


    @SneakyThrows
    @PostMapping(path="/functionParse")
    public ResponseContent ParseFunctionCsv(@RequestParam("project_id") String projectId,@RequestParam("csv_file") MultipartFile csvFile)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        String content=new String(csvFile.getBytes());

        result.setResult(new ImmutablePair<>("functions",
                                             projectFunctionService.ParseFunctionCsv(projectId,
                                                                                     content)));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @GetMapping(path="/function/download/{project_id}", produces=MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody
    String DownloadProjectFunctions(@PathVariable(name="project_id") String projectId)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        return projectFunctionService.GetFunctionCsv(projectId);
    }

    @SneakyThrows
    @PutMapping(path="/function/{project_id}")
    public ResponseContent UpdateProjectFunctions(@PathVariable(name="project_id") String projectId,@RequestBody Map<String,Object> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        List<Map<String,String>> functions=objectMapper.convertValue(params.get("functions"),
                                                                  new TypeReference<List<Map<String,String>>>()
                                                                  {
                                                                  });

        projectFunctionService.UpdateFunctions(projectId,
                                               functions);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @GetMapping(path="/function/{project_id}")
    public ResponseContent GetProjectFunctions(@PathVariable(name="project_id") String projectId)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        ImmutablePair<List<Map<String,String>>,List<Map<String,String>>> queryResult=projectFunctionService.ClassifyFunctionByIsSuperior(projectId);
        result.setResult(Map.of("first_level_functions",
                                queryResult.left,
                                "second_level_functions",
                                queryResult.right));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @GetMapping(path="/function/newId")
    public ResponseContent GetNewId()
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        result.setResult(projectFunctionService.getNewId());
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

}
