package com.april.achieveit_userinfo.controller;

import com.april.achieveit_common.bean.ResponseContent;
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
    ObjectMapper objectMapper;

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
        return null;
    }

    @Override
    @PostMapping(path="/userProjectRole/batch")
    public ResponseContent BatchGetUserProjectRole(@RequestBody Map<String,String> params)
    {
        return null;
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
        return null;
    }

    @Override
    @GetMapping(path="/globalRole")
    public ResponseContent GetUserGlobalRole(@RequestBody Map<String,String> params)
    {
        return null;
    }
}
