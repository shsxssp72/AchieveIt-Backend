package com.april.achieveit_userinfo_interface.api;

import com.april.achieveit_common.bean.ResponseContent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface RoleServiceApi
{
    @PostMapping(path="/user/userProjectRole")
    ResponseContent UpdateUserProjectRole(@RequestBody Map<String,String> params);

    @GetMapping(path="/user/userProjectRole")
    ResponseContent GetUserProjectRole(@RequestBody Map<String,String> params);

    @PostMapping(path="/user/userProjectRole/batch")
    ResponseContent BatchGetUserProjectRole(@RequestBody Map<String,String> params);

    @PostMapping(path="/user/projectRole")
    ResponseContent UpdateProjectMember(@RequestBody Map<String,String> params);

    @GetMapping(path="/user/projectRole")
    ResponseContent GetProjectMember(@RequestBody Map<String,String> params);

    @GetMapping(path="/user/globalRole")
    public ResponseContent GetUserGlobalRole(@RequestBody Map<String,String> params);
}
