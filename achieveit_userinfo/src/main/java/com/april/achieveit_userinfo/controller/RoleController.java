package com.april.achieveit_userinfo.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_userinfo.service.AuthorizationService;
import com.april.achieveit_userinfo_interface.api.RoleServiceApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/user")
public class RoleController implements RoleServiceApi
{
    //TODO Placeholder

    private static Logger logger=LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthorizationService authorizationService;

    @Override
    @PostMapping(path="/userProjectRole")
    public ResponseContent UpdateUserProjectRole(@RequestBody Map<String,String> params)
    {
        return null;
    }

    /**
     * When project_id is not null, return user's role under the specific project, otherwise the user's role under all project
     */
    @Override
    @GetMapping(path="/userProjectRole")
    public ResponseContent GetUserProjectRole(@RequestBody Map<String,String> params)//TODO Different behavior with/without project_id
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String projectId=params.getOrDefault("projectId",
                                             null);
        String userId=params.get("user_id");

        if(projectId==null)
            result.setResult(authorizationService.GetUserRoleFromMultipleProject(userId));
        else
            result.setResult(authorizationService.GetUserProjectRole(projectId,
                                                                     userId));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @Override
    @PostMapping(path="/userProjectRole/batch")
    public ResponseContent BatchGetUserProjectRole(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        List<String> userIds=objectMapper.readValue(params.get("user_ids"),
                                                    new TypeReference<List<String>>()
                                                    {
                                                    });
        result.setResult(authorizationService.BatchGetUserRoleFromMultipleProject(userIds));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    /**
     * Will delete all existing project member
     */
    @SneakyThrows
    @Override
    @PostMapping(path="/projectRole")
    public ResponseContent UpdateProjectMember(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());

        ResponseContent result=new ResponseContent();

        String members=params.get("members");
        List<Map<String,String>> memberList=objectMapper.readValue(members,
                                                                   new TypeReference<List<Map<String,String>>>()
                                                                   {
                                                                   });
        //TODO Complete the logic
        return result;
    }

    @Override
    @GetMapping(path="/projectRole")
    public ResponseContent GetProjectMember(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String projectId=params.get("project_id");
        result.setResult(authorizationService.GetProjectMember(projectId));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @Override
    @GetMapping(path="/globalRole")
    public ResponseContent GetUserGlobalRole(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String userId=params.get("user_id");
        result.setResult(authorizationService.GetUserGlobalRole(userId));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @Override
    @GetMapping(path="/inferior")
    public ResponseContent GetInferior(Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String superiorId=params.get("superior_id");
        result.setResult(authorizationService.GetInferior(superiorId));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @Override
    @GetMapping(path="/permission")
    public ResponseContent GetUserPermission(Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String projectId=params.get("projectId");
        String userId=params.get("user_id");

        result.setResult(authorizationService.GetUserPermissionName(projectId,
                                                                    userId));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @Override
    @PostMapping(path="/permission")
    public ResponseContent UpdateUserPermission(Map<String,String> params)
    {
        return null;
    }
}
