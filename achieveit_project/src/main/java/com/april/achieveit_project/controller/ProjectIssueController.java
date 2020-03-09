package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/project/issue")
public class ProjectIssueController
{
    //TODO Placeholder

    private static Logger logger=LoggerFactory.getLogger(ProjectIssueController.class);

    @GetMapping(path="/{project_id}")
    public ResponseContent GetProjectIssueTrackerUrl(@PathVariable(name="project_id") String project_id)
    {
        return null;
    }

    @PostMapping(path="/{project_id}")
    public ResponseContent GetProjectIssueTrackerUrl(@PathVariable(name="project_id") String project_id,@RequestBody Map<String,String> params)
    {
        return null;
    }
}
