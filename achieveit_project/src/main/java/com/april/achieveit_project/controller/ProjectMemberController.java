package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_userinfo_interface.api.RoleServiceApi;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/project/memberConf")
public class ProjectMemberController
{
    private static Logger logger=LoggerFactory.getLogger(ProjectMemberController.class);
    @Autowired
    RoleServiceApi roleServiceApi;

    @SneakyThrows
    @PostMapping(path="/{project_id}")
    public ResponseContent UpdateProjectMemberInfo(@PathVariable(name="project_id") String projectId,@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        params.put("project_id",
                   projectId);
        return roleServiceApi.UpdateProjectMember(params);
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
