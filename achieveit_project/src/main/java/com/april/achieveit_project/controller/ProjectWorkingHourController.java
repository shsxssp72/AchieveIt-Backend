package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_project.entity.WorkingHour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/project/workingHour")
public class ProjectWorkingHourController
{
    //TODO Placeholder

    private static Logger logger=LoggerFactory.getLogger(ProjectWorkingHourController.class);

    @PostMapping(path="/{project_id}")
    public ResponseContent AddWorkingHour(@PathVariable(name="project_id") String project_id,@RequestBody WorkingHour workingHour)
    {
        return null;
    }

    @GetMapping(path="/{project_id}")
    public ResponseContent GetWorkingHour(@PathVariable(name="project_id") String project_id)
    {
        return null;
    }

    @PostMapping(path="/{project_id}/{working_hour_id}")
    public ResponseContent UpdateWorkingHour(@PathVariable(name="project_id") String project_id,@PathVariable(name="working_hour_id") String working_hour_id,@RequestBody WorkingHour workingHour)
    {
        return null;
    }

    @GetMapping(path="/{project_id}/{working_hour_id}")
    public ResponseContent GetDetailedWorkingHour(@PathVariable(name="project_id") String project_id,@PathVariable(name="working_hour_id") String working_hour_id)
    {
        return null;
    }

    @PostMapping(path="/verify")
    public ResponseContent VerifyWorkingHour(@RequestBody Map<String,String> params)
    {
        return null;
    }
    @GetMapping(path="/verify")
    public ResponseContent GetToBeVerifiedWorkingHour()
    {
        return null;
    }


    @GetMapping("/activityType")
    public ResponseContent GetActivityType()
    {
        return null;
    }

}
