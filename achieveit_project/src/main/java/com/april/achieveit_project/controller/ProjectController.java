package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_common.utility.JsonVisibilityLevel;
import com.april.achieveit_project.config.ProjectStateTransition;
import com.april.achieveit_project.entity.Project;
import com.april.achieveit_project.service.DependencyService;
import com.april.achieveit_project.service.ProjectService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(path="/project")
public class ProjectController
{
    //TODO Placeholder

    private static Logger logger=LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    DependencyService dependencyService;
    @Autowired
    ProjectService projectService;

    @PostMapping(path="/setUp")
    public ResponseContent SetUpProject(@RequestBody Project project)
    {
        ResponseContent result=new ResponseContent();
        project.setStatus(ProjectStateTransition.ProjectState.Applied);
        projectService.NewProject(project);

        result.setMessage(dependencyService.sendEmail());
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PostMapping(path="/search")
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    public ResponseContent SearchProject(@RequestBody Map<String,String> params,HttpServletRequest request)
    {
        return null;
    }

    @GetMapping(path="/listRelative")
    public ResponseContent ListRelativeProject(HttpServletRequest request)
    {
        return null;
    }

    @PostMapping(path="/detail/{project_id}")
    public ResponseContent UpdateProjectInformation(@PathVariable(name="project_id") String project_id,@RequestBody Project project)
    {
        return null;
    }

    @GetMapping(path="/detail/{project_id}")
    public ResponseContent GetProjectInformation(@PathVariable(name="project_id") String project_id)
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
