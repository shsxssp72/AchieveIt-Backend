package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_common.utility.CookieUtility;
import com.april.achieveit_common.utility.JWTUtility;
import com.april.achieveit_common.utility.JsonVisibilityLevel;
import com.april.achieveit_project.config.ProjectStateTransition;
import com.april.achieveit_project.entity.Project;
import com.april.achieveit_project.service.DependencyService;
import com.april.achieveit_project.service.ProjectService;
import com.april.achieveit_userinfo_interface.api.RoleServiceApi;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(path="/project")
public class ProjectController
{
    private static Logger logger=LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    DependencyService dependencyService;
    @Autowired
    ProjectService projectService;
    @Autowired
    RoleServiceApi roleServiceApi;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping(path="/setUp")
    public ResponseContent SetUpProject(@RequestBody Project project)//TODO Check if projectId valid
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        project.setStatus(ProjectStateTransition.ProjectState.Applied);
        projectService.NewProject(project);

        result.setMessage(dependencyService.sendEmail());
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
    @GetMapping(path="/listRelative")
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

        //TODO Code below not tested, should be tested when userinfo is complete
        ResponseContent roleQueryResult=roleServiceApi.GetUserProjectRole(new HashMap<>()
        {{
            put("user_id",
                userId);
        }});
        Map<String,List<String>> userRoles=objectMapper.convertValue(roleQueryResult.getResult(),
                                                                     new TypeReference<TreeMap<String,List<String>>>()
                                                                     {
                                                                     });

        List<Project> queryResult=projectService.ListRelativeProject(userRoles.keySet(),
                                                                     pageSize,
                                                                     currentPage);
        result.setStatus(ResponseContentStatus.SUCCESS);
        result.setResult(queryResult);
        return result;
    }

    @PostMapping(path="/detail/{project_id}")
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

    @PostMapping(path="/status/{project_id}")
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

}
//TODO Add Redis support for search and other getter