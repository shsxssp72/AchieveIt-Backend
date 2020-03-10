package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_common.utility.CookieUtility;
import com.april.achieveit_common.utility.JWTUtility;
import com.april.achieveit_project.service.ProjectService;
import com.april.achieveit_userinfo_interface.api.RoleServiceApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/project/memberConf")
public class ProjectMemberController
{
    private static Logger logger=LoggerFactory.getLogger(ProjectMemberController.class);
    @Autowired
    RoleServiceApi roleServiceApi;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProjectService projectService;

    @SneakyThrows
    @PostMapping(path="/{project_id}")
    public ResponseContent UpdateProjectMemberInfo(@PathVariable(name="project_id") String projectId,@RequestBody Map<String,String> params,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        params.put("project_id",
                   projectId);
        var updateResult=roleServiceApi.UpdateProjectMember(params);

        if(updateResult.getStatus()==ResponseContentStatus.SUCCESS)
        {
            String jwt=CookieUtility.getCookieValue(request,"JWT");
            String userId=JWTUtility.getSubjectFromJWT(jwt);

            //TODO When QA Manager, EPG Leader, Conf. Manager add members. allow PM to push project forward.
            ResponseContent queryResponse=roleServiceApi.GetUserGlobalRole(new HashMap<>(){{put("user_id",userId);}});
            Map<String,String> queryResult=objectMapper.convertValue(queryResponse.getResult(),
                                                                     new TypeReference<Map<String,String>>()
                                      {
                                      });
            String global_role_name=queryResult.get("global_role_name");
            projectService.UpdateProjectMiscWhenMemberUpdated(projectId,global_role_name);
        }

        return updateResult;
    }

    @GetMapping(path="/{project_id}")
    public ResponseContent GetProjectMemberInfo(@PathVariable(name="project_id") String projectId)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        Map<String,String> params=new HashMap<>()
        {{
            put("project_id",
                projectId);
        }};
        return roleServiceApi.GetProjectMember(params);
    }
}
