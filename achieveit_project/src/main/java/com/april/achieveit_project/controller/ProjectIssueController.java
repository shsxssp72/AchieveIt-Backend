package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_project.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/issue")
public class ProjectIssueController
{
    private static Logger logger=LoggerFactory.getLogger(ProjectIssueController.class);
    @Autowired
    ProjectService projectService;

    @GetMapping(path="/{project_id}")
    public ResponseContent GetProjectIssueTrackerUrl(@PathVariable(name="project_id") String projectId)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String issueTrackerUrl=projectService.SelectMiscByProjectIdAndKey(projectId,
                                                                          "issue_tracker_url");
        result.setResult(issueTrackerUrl);
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PostMapping(path="/{project_id}")
    public ResponseContent GetProjectIssueTrackerUrl(@PathVariable(name="project_id") String projectId,@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String issueTrackerUrl=params.get("issue_tracker_url");
        projectService.InsertMiscByProjectIdAndKey(projectId,
                                                   "issue_tracker_url",
                                                   issueTrackerUrl);
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }
}
