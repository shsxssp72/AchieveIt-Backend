package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_common.utility.CookieUtility;
import com.april.achieveit_common.utility.JWTUtility;
import com.april.achieveit_common.utility.JsonVisibilityLevel;
import com.april.achieveit_project.entity.WorkingHour;
import com.april.achieveit_project.service.ProjectWorkingHourService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/project/workingHour")
public class ProjectWorkingHourController
{
    private static Logger logger=LoggerFactory.getLogger(ProjectWorkingHourController.class);

    @Autowired
    private ProjectWorkingHourService projectWorkingHourService;

    @SneakyThrows
    @PostMapping(path="/{project_id}")
    public ResponseContent AddWorkingHour(@PathVariable(name="project_id") String projectId,@RequestBody WorkingHour workingHour,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);
        workingHour.setReferredProjectId(projectId);
        workingHour.setReferredUserId(userId);

        projectWorkingHourService.NewWorkingHour(workingHour);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    @GetMapping(path="/{project_id}")
    public ResponseContent GetWorkingHour(@PathVariable(name="project_id") String projectId,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);

        var queryResult=projectWorkingHourService.SelectByProjectId(projectId,
                                                                    userId);
        result.setResult(queryResult);
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @PostMapping(path="/{project_id}/{working_hour_id}")
    public ResponseContent UpdateWorkingHour(@PathVariable(name="project_id") String projectId,@PathVariable(name="working_hour_id") String workingHourId,@RequestBody WorkingHour workingHour,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);

        workingHour.setReferredProjectId(projectId);
        workingHour.setWorkingHourId(Long.parseLong(workingHourId));
        projectWorkingHourService.UpdateWorkingHour(workingHour,
                                                    userId);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @GetMapping(path="/{project_id}/{working_hour_id}")
    public ResponseContent GetDetailedWorkingHour(@PathVariable(name="project_id") String projectId,@PathVariable(name="working_hour_id") String workingHourId,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);

        WorkingHour queryResult=projectWorkingHourService.SelectByWorkingHourId(Long.parseLong(workingHourId),
                                                                                userId);
        result.setResult(queryResult);
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @PostMapping(path="/verify")
    public ResponseContent VerifyWorkingHour(@RequestBody Map<String,String> params,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);

        var workingHourId=Long.parseLong(params.get("working_hour_id"));
        var verified=Boolean.parseBoolean(params.get("verified"));

        projectWorkingHourService.VerifyWorkingHour(workingHourId,
                                                    verified,
                                                    userId);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @GetMapping(path="/verify")
    public ResponseContent GetToBeVerifiedWorkingHour(HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);
        List<WorkingHour> queryResult=projectWorkingHourService.GetToBeVerifiedWorkingHour(null,
                                                                                           userId);
        result.setResult(queryResult);
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }


    @GetMapping("/activityType")
    public ResponseContent GetActivityType()
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        result.setResult(projectWorkingHourService.ListAllActivityType());
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

}
