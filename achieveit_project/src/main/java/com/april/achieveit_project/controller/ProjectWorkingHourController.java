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
@RequestMapping(path="/workingHour")
public class ProjectWorkingHourController
{
    private static Logger logger=LoggerFactory.getLogger(ProjectWorkingHourController.class);

    @Autowired
    private ProjectWorkingHourService projectWorkingHourService;

    @SneakyThrows
    @PutMapping(path="/{project_id}")
    public ResponseContent AddWorkingHour(@PathVariable(name="project_id") String projectId,@RequestBody Map<String,String> params,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);

        params.put("referred_user_id", userId);
        params.put("verified","false");
        WorkingHour workingHour=projectWorkingHourService.assembleWorkingHour(projectId,params);


        projectWorkingHourService.NewWorkingHour(workingHour);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    /**
     * Note that when working hour referred to a non-existing functionId, it should show that function has been modified
     */
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

        var queryResult=projectWorkingHourService.SelectByProjectIdAndUserId(projectId,
                                                                             userId);
        result.setResult(queryResult);
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @PutMapping(path="/{project_id}/{working_hour_id}")
    public ResponseContent UpdateWorkingHour(@PathVariable(name="project_id") String projectId,@PathVariable(name="working_hour_id") String workingHourId,@RequestBody Map<String,String> params,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);

        params.put("referred_user_id", userId);
        params.put("working_hour_id", workingHourId);
        WorkingHour workingHour=projectWorkingHourService.assembleWorkingHour(projectId,params);

        projectWorkingHourService.UpdateWorkingHour(workingHour,
                                                    userId);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
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
    @PutMapping(path="/verify")
    public ResponseContent VerifyWorkingHour(@RequestBody Map<String,String> params,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);

        long workingHourId=Long.parseLong(params.get("working_hour_id"));
        boolean verified=Boolean.parseBoolean(params.get("verified"));

        projectWorkingHourService.VerifyWorkingHour(workingHourId,
                                                    verified,
                                                    userId);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    @PostMapping(path="/verify")
    public ResponseContent GetToBeVerifiedWorkingHour(@RequestBody Map<String,String> params,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);
        String projectId=params.get("project_id");
        List<WorkingHour> queryResult=projectWorkingHourService.GetToBeVerifiedWorkingHour(projectId,
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
