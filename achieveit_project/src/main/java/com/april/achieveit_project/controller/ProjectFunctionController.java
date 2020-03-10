package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping(path="/project")
public class ProjectFunctionController
{
    //TODO Placeholder

    private static Logger logger=LoggerFactory.getLogger(ProjectFunctionController.class);

    @PostMapping(path="/functionParse")
    public ResponseContent ParseFunctionCsv(@RequestParam("csv_file") MultipartFile csvFile)
    {
        return null;
    }

    @PostMapping(path="/function/{project_id}")
    public ResponseContent UpdateProjectFunctions(@PathVariable(name="project_id") String projectId,@RequestBody Map<String,String> params)
    {
        return null;
    }

    @GetMapping(path="/function/{project_id}")
    public ResponseContent GetProjectFunctions(@PathVariable(name="project_id") String projectId)
    {
        return null;
    }

    @GetMapping(path="/function/download/{project_id}", produces=MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody
    String DownloadProjectFunctions(@PathVariable(name="project_id") String projectId)
    {
        return null;
    }

}
