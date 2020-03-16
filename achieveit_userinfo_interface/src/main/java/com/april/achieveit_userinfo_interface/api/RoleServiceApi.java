package com.april.achieveit_userinfo_interface.api;

import com.april.achieveit_common.bean.ResponseContent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RoleServiceApi
{
    @PostMapping(path="/user/userProjectRole")
    ResponseContent UpdateUserProjectRole(@RequestBody Map<String,String> params);

    @GetMapping(path="/user/userProjectRole")
    ResponseContent GetUserProjectRole(@RequestBody Map<String,String> params);

    @PostMapping(path="/user/userProjectRole/batch")
    ResponseContent BatchGetUserProjectRole(@RequestBody Map<String,String> params);

    @PostMapping(path="/user/projectMember")
    ResponseContent UpdateProjectMember(@RequestBody Map<String,String> params);

    @GetMapping(path="/user/projectMember")
    ResponseContent GetProjectMember(@RequestBody Map<String,String> params);

    @GetMapping(path="/user/globalRole")
    ResponseContent GetUserGlobalRole(@RequestBody Map<String,String> params);

    @GetMapping(path="/user/globalRole")
    ResponseContent GetSelfGlobalRole(HttpServletRequest request);

    @GetMapping(path="/user/inferior")
    ResponseContent GetInferior(@RequestBody Map<String,String> params);

    @GetMapping(path="/user/permission")
    ResponseContent GetUserPermission(@RequestBody Map<String,String> params);

    @PostMapping(path="/user/permission")
    ResponseContent UpdateUserPermission(Map<String,String> params);
}
