package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_project.entity.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/project")
public class ProjectController
{
    //TODO Placeholder

    private static Logger logger=LoggerFactory.getLogger(ProjectController.class);

    @PostMapping(path="/setUp")
    public ResponseContent SetUpProject(@RequestBody Project project)
    {
        return null;
    }

    @PostMapping(path="/search")
    public ResponseContent SearchProject(@RequestBody Map<String,String> params)
    {
        return null;
    }

    @GetMapping(path="/listRelative")
    public ResponseContent ListRelativeProject()
    {
        return null;
    }

    @PostMapping(path="/detail/{project_id}")
    public ResponseContent UpdateProjectInformation(@PathVariable(name="project_id") String project_id,@RequestBody Project project)
    {
        return null;
    }

    @GetMapping(path="/detail/{project_id}")
    public ResponseContent UpdateProjectInformation(@PathVariable(name="project_id") String project_id)
    {
        return null;
    }

    @PostMapping(path="/status/{project_id}")
    public ResponseContent UpdateProjectStatus(@PathVariable(name="project_id") String project_id,@RequestBody Map<String,String> params)
    {
        return null;
    }

    @GetMapping(path="/status/{project_id}")
    public ResponseContent GetProjectStatus(@PathVariable(name="project_id") String project_id)
    {
        return null;
    }

}
