package com.april.achieveit_userinfo_interface.api;

import com.april.achieveit_common.bean.ResponseContent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RoleServiceApi
{
    @PostMapping(path="/userProjectRole")
    ResponseContent UpdateUserProjectRole(@RequestBody Map<String,String> params);

    @GetMapping(path="/userProjectRole")
    ResponseContent GetUserProjectRole(@RequestBody Map<String,String> params);

    @PostMapping(path="/userProjectRole/batch")
    ResponseContent BatchGetUserProjectRole(@RequestBody Map<String,String> params);

    @PostMapping(path="/projectMember")
    ResponseContent UpdateProjectMember(@RequestBody Map<String,String> params);

    @GetMapping(path="/projectMember")
    ResponseContent GetProjectMember(@RequestBody Map<String,String> params);

    @GetMapping(path="/globalRole")
    ResponseContent GetUserGlobalRole(@RequestBody Map<String,String> params);

    @GetMapping(path="/globalRole/self")
    ResponseContent GetSelfGlobalRole(HttpServletRequest request);

    @GetMapping(path="/inferior")
    ResponseContent GetInferior(@RequestBody Map<String,String> params);

    @GetMapping(path="/permission")
    ResponseContent GetUserPermission(@RequestBody Map<String,String> params);

    @PostMapping(path="/permission")
    ResponseContent UpdateUserPermission(Map<String,String> params);
}
