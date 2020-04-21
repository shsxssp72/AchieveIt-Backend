package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_common.utility.CookieUtility;
import com.april.achieveit_common.utility.JWTUtility;
import com.april.achieveit_project.client.RoleServiceClient;
import com.april.achieveit_project.config.ProjectStateTransition;
import com.april.achieveit_project.entity.Project;
import com.april.achieveit_project.service.ProjectService;
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
@RequestMapping(path="/memberConf")
public class ProjectMemberController
{
    private static Logger logger=LoggerFactory.getLogger(ProjectMemberController.class);
    @Autowired
    RoleServiceClient roleServiceClient;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProjectService projectService;

    @SneakyThrows
    @PutMapping(path="/{project_id}")
    public ResponseContent UpdateProjectMemberInfo(@PathVariable(name="project_id") String projectId,@RequestBody Map<String,Object> params,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        Project currentProject=projectService.SelectByProjectId(projectId);
        if(ProjectStateTransition.ProjectState.Finished.name()
                .equals(currentProject.getStatus())||ProjectStateTransition.ProjectState.ReadyArchive.name()
                .equals(currentProject.getStatus())||ProjectStateTransition.ProjectState.Archived.name()
                .equals(currentProject.getStatus())||ProjectStateTransition.ProjectState.ArchiveDeclined.name()
                .equals(currentProject.getStatus()))
        {
            throw new IllegalArgumentException("Finished project cannot modify member.");
        }

        params.put("project_id",
                   projectId);
        var updateResult=roleServiceClient.UpdateProjectMember(params);

        if(updateResult.getStatus()==ResponseContentStatus.SUCCESS)
        {
            String jwt=CookieUtility.getCookieValue(request,
                                                    "JWT");
            String userId=JWTUtility.getSubjectFromJWT(jwt);

            //When QA Manager, EPG Leader, Conf. Manager add members. allow PM to push project forward.
            String global_role_name=projectService.getUserGlobalRole(userId);
            projectService.UpdateProjectMiscWhenMemberUpdated(projectId,
                                                              global_role_name);
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
        return roleServiceClient.GetProjectMember(params);
    }
}
