package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_project.entity.Risk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/project/risk")
public class ProjectRiskController
{
    //TODO Placeholder

    private static Logger logger=LoggerFactory.getLogger(ProjectRiskController.class);

    @PostMapping(path="/{project_id}")
    public ResponseContent AddRisk(@PathVariable(name="project_id") String projectId,@RequestBody Risk risk)
    {
        return null;
    }
    @GetMapping(path="/{project_id}")
    public ResponseContent GetRisk(@PathVariable(name="project_id") String projectId)
    {
        return null;
    }

    @PostMapping(path="/{project_id}/{risk_id}")
    public ResponseContent AddRisk(@PathVariable(name="project_id") String projectId,@PathVariable(name="risk_id") String riskId,@RequestBody Risk risk)
    {
        return null;
    }

    @GetMapping("/riskType")
    public ResponseContent GetRiskType()
    {
        return null;
    }
}
