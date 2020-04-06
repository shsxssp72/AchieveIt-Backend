package com.april.achieveit_userinfo_interface.api;

import com.april.achieveit_common.bean.ResponseContent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RoleServiceApi
{
    @PutMapping(path="/userProjectRole")
    ResponseContent UpdateUserProjectRole(@RequestBody Map<String,String> params);

    @PostMapping(path="/userProjectRole")
    ResponseContent GetUserProjectRole(@RequestBody Map<String,String> params);

    @PostMapping(path="/userProjectRole/batch")
    ResponseContent BatchGetUserProjectRole(@RequestBody Map<String,String> params);

    @PutMapping(path="/projectMember")
    ResponseContent UpdateProjectMember(@RequestBody Map<String,Object> params);

    @PostMapping(path="/projectMember")
    ResponseContent GetProjectMember(@RequestBody Map<String,String> params);

    @PostMapping(path="/globalRole")
    ResponseContent GetUserGlobalRole(@RequestBody Map<String,String> params);

    @GetMapping(path="/globalRole/self")
    ResponseContent GetSelfGlobalRole(HttpServletRequest request);

    @PostMapping(path="/inferior")
    ResponseContent GetInferior(@RequestBody Map<String,String> params);

    @PostMapping(path="/permission")
    ResponseContent GetUserPermission(@RequestBody Map<String,String> params);

    @PutMapping(path="/permission")
    ResponseContent UpdateUserPermission(@RequestBody Map<String,Object> params);

    @GetMapping(path="/listProjectRole")
    ResponseContent ListProjectRole();

    @GetMapping(path="/listEditablePermission")
    ResponseContent ListEditablePermission();

    @PostMapping(path="/getOuterUserId")
    ResponseContent GetOuterUserId(@RequestBody Map<String,Object> params);
}
