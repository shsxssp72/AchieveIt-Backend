package com.april.achieveit_userinfo.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_common.utility.CookieUtility;
import com.april.achieveit_common.utility.JWTUtility;
import com.april.achieveit_userinfo.service.AuthorizationService;
import com.april.achieveit_userinfo_interface.api.RoleServiceApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/")
public class RoleController implements RoleServiceApi
{
    private static Logger logger=LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthorizationService authorizationService;

    @SneakyThrows
    @Override
    @PutMapping(path="/userProjectRole")
    public ResponseContent UpdateUserProjectRole(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String projectId=params.get("project_id");
        String userId=params.get("user_id");
        List<Map<String,String>> projectRoleIdList=objectMapper.readValue(params.get("project_role_id_list"),
                                                                          new TypeReference<List<Map<String,String>>>()
                                                                          {
                                                                          });
        authorizationService.UpdateUserProjectRole(projectId,
                                                   userId,
                                                   projectRoleIdList);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    /**
     * When project_id is not null, return user's role under the specific project, otherwise the user's role under all project
     */
    @Override
    @PostMapping(path="/userProjectRole")
    public ResponseContent GetUserProjectRole(@RequestBody Map<String,String> params)//! Different behavior with/without project_id
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String projectId=params.getOrDefault("project_id",
                                             null);
        String userId=params.get("user_id");

        if(projectId==null)
            result.setResult(authorizationService.GetUserRoleFromMultipleProject(userId));
        else
            result.setResult(List.of(authorizationService.GetUserProjectRole(projectId,
                                                                     userId)));

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
    @PutMapping(path="/projectMember")
    public ResponseContent UpdateProjectMember(@RequestBody Map<String,Object> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());

        ResponseContent result=new ResponseContent();

        String projectId=(String)params.get("project_id");
        Object members=params.get("members");
        List<Map<String,String>> memberList=objectMapper.convertValue(members,
                                                                   new TypeReference<List<Map<String,String>>>()
                                                                   {
                                                                   });
        authorizationService.UpdateProjectMember(projectId,
                                                 memberList);
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @Override
    @PostMapping(path="/projectMember")
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
    @PostMapping(path="/globalRole")
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
    @SneakyThrows
    @GetMapping(path="/globalRole/self")
    public ResponseContent GetSelfGlobalRole(HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);

        return GetUserGlobalRole(Map.of("user_id",
                                        userId));
    }

    @Override
    @PostMapping(path="/inferior")
    public ResponseContent GetInferior(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String projectId=params.getOrDefault("project_id",
                                             null);
        String superiorId=params.get("superior_id");
        result.setResult(authorizationService.GetInferior(projectId,
                                                          superiorId));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @Override
    @PostMapping(path="/permission")
    public ResponseContent GetUserPermission(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String projectId=params.getOrDefault("project_id",
                                             null);
        String userId=params.get("user_id");

        result.setResult(authorizationService.GetUserPermissionName(projectId,
                                                                    userId));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @Override
    @PutMapping(path="/permission")
    public ResponseContent UpdateUserPermission(@RequestBody Map<String,Object> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String userId=(String)params.get("user_id");
        String projectId=(String)params.get("project_id");
        List<String> permissionList=objectMapper.convertValue(params.get("privilege_list"),
                                                           new TypeReference<List<String>>()
                                                           {
                                                           });
        authorizationService.UpdateUserProjectPermission(projectId,
                                                         userId,
                                                         permissionList);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @Override
    @GetMapping(path="/listProjectRole")
    public ResponseContent ListProjectRole()
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        result.setResult(authorizationService.getAllProjectRoles());

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @Override
    @GetMapping(path="/listEditablePermission")
    public ResponseContent ListEditablePermission()
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        result.setResult(authorizationService.getEditablePermissions());

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

}
