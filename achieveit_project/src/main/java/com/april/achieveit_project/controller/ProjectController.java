package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_common.utility.CookieUtility;
import com.april.achieveit_common.utility.JWTUtility;
import com.april.achieveit_common.utility.JsonVisibilityLevel;
import com.april.achieveit_project.client.RoleServiceClient;
import com.april.achieveit_project.config.ProjectStateTransition;
import com.april.achieveit_project.entity.Project;
import com.april.achieveit_project.service.DependencyService;
import com.april.achieveit_project.service.ProjectService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/")
public class ProjectController
{
    private static Logger logger=LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    DependencyService dependencyService;
    @Autowired
    ProjectService projectService;
    @Autowired
    RoleServiceClient roleServiceClient;
    @Autowired
    ObjectMapper objectMapper;

    @PutMapping(path="/setUp")
    public ResponseContent SetUpProject(@RequestBody Project project)//TODO Check if projectId valid
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        project.setStatus(ProjectStateTransition.ProjectState.Applied);
        projectService.NewProject(project);

        result.setMessage(dependencyService.sendEmail("","",""));
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PostMapping(path="/getByStatus")
    public ResponseContent GetProjectByStatus(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String status=params.get("status");
        List<Project> queryResult=projectService.SelectByProjectStatus(ProjectStateTransition.ProjectState.valueOf(status));

        result.setResult(queryResult);
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PostMapping(path="/search")
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    public ResponseContent SearchProject(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        String key_word=params.get("key_word");//Empty is acceptable
        int pageSize=Integer.parseInt(params.get("page_size"));
        int currentPage=Integer.parseInt(params.get("current_page"));

        List<Project> queryResult=projectService.SearchProjectByName(key_word,
                                                                     pageSize,
                                                                     currentPage);

        result.setStatus(ResponseContentStatus.SUCCESS);
        result.setResult(queryResult);
        return result;
    }

    @SneakyThrows
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    @PostMapping(path="/listRelative")
    public ResponseContent ListRelativeProject(@RequestBody Map<String,String> params,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        int pageSize=Integer.parseInt(params.get("page_size"));
        int currentPage=Integer.parseInt(params.get("current_page"));
        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);

        ResponseContent roleQueryResult=roleServiceClient.GetUserProjectRole(new HashMap<>()
        {{
            put("user_id",
                userId);
        }});
        List<Map<String,String>> userRoleInfo=objectMapper.convertValue(roleQueryResult.getResult(),
                                                                     new TypeReference<List<Map<String,String>>>()
                                                                     {
                                                                     });
        Set<String> relatedProjectIds=userRoleInfo.parallelStream().map(i->i.get("project_id")).collect(Collectors.toSet());

        List<Project> queryResult=projectService.SelectByProjectIds(relatedProjectIds,
                                                                    pageSize,
                                                                    currentPage);
        result.setStatus(ResponseContentStatus.SUCCESS);
        result.setResult(queryResult);
        return result;
    }

    @PutMapping(path="/detail/{project_id}")
    public ResponseContent UpdateProjectInformation(@PathVariable(name="project_id") String projectId,@RequestBody Project project)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        project.setProjectId(projectId);

        projectService.UpdateProjectInfo(project);
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @GetMapping(path="/detail/{project_id}")
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    public ResponseContent GetProjectInformation(@PathVariable(name="project_id") String projectId)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        var queryResult=projectService.SelectByProjectId(projectId);

        result.setResult(queryResult);
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PutMapping(path="/status/{project_id}")
    public ResponseContent UpdateProjectStatus(@PathVariable(name="project_id") String projectId,@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String status=params.get("status");
        projectService.UpdateProjectStatus(projectId,
                                           status);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @GetMapping(path="/status/{project_id}")
    public ResponseContent GetProjectStatus(@PathVariable(name="project_id") String projectId)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        result.setResult(new ImmutablePair<>("status",
                                             projectService.SelectByProjectId(projectId)
                                                     .getStatus()));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @PostMapping(path="/confirmConfigEstablished")
    public ResponseContent ConfirmConfigEstablished(@RequestBody Map<String,String> params,HttpServletRequest request)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        String projectId=params.get("project_id");String jwt=CookieUtility.getCookieValue(request,
                                                                                          "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);
        ResponseContent queryResponse=roleServiceClient.GetUserGlobalRole(new HashMap<>(){{put("user_id",userId);}});
        Map<String,String> queryResult=objectMapper.convertValue(queryResponse.getResult(),
                                                                 new TypeReference<Map<String,String>>()
                                                                 {
                                                                 });
        String global_role_name=queryResult.get("global_role_name");

        projectService.ConfirmConfigEstablished(projectId,global_role_name);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

}