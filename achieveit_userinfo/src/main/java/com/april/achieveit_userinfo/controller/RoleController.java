package com.april.achieveit_userinfo.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_userinfo_interface.api.RoleServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/user")
public class RoleController implements RoleServiceApi
{
    //TODO Placeholder

    private static Logger logger=LoggerFactory.getLogger(RoleController.class);

    @Override
    @PostMapping(path="/projectRole")
    public ResponseContent UpdateUserProjectRole(@RequestBody Map<String,String> params)
    {
        return null;
    }

    @Override
    @GetMapping(path="/projectRole")
    public ResponseContent GetUserProjectRole(@RequestBody Map<String,String> params)//TODO Different behavior with/without project_id
    {
        return null;
    }

    @Override
    @PostMapping(path="/projectRole/batch")
    public ResponseContent BatchGetUserProjectRole(@RequestBody Map<String,String> params)
    {
        return null;
    }
}
