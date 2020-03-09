package com.april.achieveit_userinfo_interface.api;

import com.april.achieveit_common.bean.ResponseContent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface RoleServiceApi
{
    @PostMapping(path="/projectRole")
    ResponseContent UpdateUserProjectRole(@RequestBody Map<String,String> params);

    @GetMapping(path="/projectRole")
    ResponseContent GetUserProjectRole(@RequestBody Map<String,String> params);

    @PostMapping(path="/projectRole/batch")
    ResponseContent BatchGetUserProjectRole(@RequestBody Map<String,String> params);
}
