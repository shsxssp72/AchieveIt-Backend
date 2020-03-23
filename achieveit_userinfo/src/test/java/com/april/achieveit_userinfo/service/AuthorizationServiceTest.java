package com.april.achieveit_userinfo.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthorizationServiceTest {
    @Autowired AuthorizationService authorizationService;

    @Test
    void getUserProjectRole() {
        Assert.assertThat(authorizationService.GetUserProjectRole("2020-4577-D-02","SYKJ-20200201-0001").get("project_id"),is("2020-4577-D-02"));

    }

    @Test
    void getUserRoleFromMultipleProject() {
        List<Map<String,String>> list =authorizationService.GetUserRoleFromMultipleProject("SYKJ-20200201-0001");
        Assert.assertThat(list.get(0).get("project_id"),is("2020-4577-D-02"));
    }

    @Test
    void batchGetUserRoleFromMultipleProject() {
        List<String> list = new ArrayList<>();
        list.add("SYKJ-20200201-0001");
        list.add("SYKJ-20200201-0002");
        List<Map<String,String>> resultList =authorizationService.BatchGetUserRoleFromMultipleProject(list);
        System.out.println(authorizationService.BatchGetUserRoleFromMultipleProject(list));
        Assert.assertThat(resultList.get(0).get("project_id"),is("2020-4577-D-02"));
        Assert.assertThat(resultList.get(1).get("project_id"),is("2020-4577-D-03"));
    }

    @Test
    void getProjectMember() {
        List<Map<String,String>> list =authorizationService.GetProjectMember("2020-4577-D-02");
        System.out.println(list);
        Assert.assertThat(list.get(0).get("project_role_name"),is("角色1"));
        Assert.assertThat(list.get(1).get("project_role_name"),is("角色3"));
    }

    @Test
    void getUserGlobalRole() {
        Map<String,String> map=authorizationService.GetUserGlobalRole("SYKJ-20200201-0001");
        Assert.assertThat(map.get("global_role_name"),is("全局1"));
    }

    @Test
    void getInferior() {
        List<String> list= authorizationService.GetInferior("2020-4577-D-02","SYKJ-20200201-0010");
        Assert.assertThat(list.get(0),is("SYKJ-20200201-0001"));
        Assert.assertThat(list.get(1),is("SYKJ-20200201-0003"));

    }

    @Test
    void getUserPermissionName() {
        List<String> list=authorizationService.GetUserPermissionName("2020-4577-D-02","SYKJ-20200201-0001");
        System.out.println(list);
        Assert.assertThat(list.get(0),is("issue_tracker_access"));
    }

    //project_role_id_list=[{"project_role_id":"123","superior_id":"SYKJ-20200201-0010","project_role_name":"角色1"}]
    @Transactional
    @Test
    void updateUserProjectRole() {
        Map<String,String> map=new HashMap<>();
        map.put("project_role_name","角色5");
        map.put("superior_id","SYKJ-20200201-0010");
        map.put("project_role_id","123");

        List<Map<String,String>> list =new ArrayList<>();
        list.add(map);
       authorizationService.UpdateUserProjectRole("2020-4577-D-02","SYKJ-20200201-0001",list);
       //Assert.assertThat(authorizationService.GetUserProjectRole("2020-4577-D-02",""));
    }

    @Transactional
    @Test
    void updateProjectMember() {
        Map<String,String> map=new HashMap<>();
        map.put("user_id","SYKJ-20200201-0004");
        map.put("project_role_id_list","[{\"project_role_id\":\"126\",\"superior_id\":\"SYKJ-20200201-0010\",\"project_role_name\":\"角色4\"}]");
        List<Map<String,String>> members = new ArrayList<>();
        members.add(map);
        authorizationService.UpdateProjectMember("2020-4577-D-02",members);
        Assert.assertThat(authorizationService.GetProjectMember("2020-4577-D-02").get(0).get("project_role_name"),is("角色4"));

    }

    @Transactional
    @Test
    void updateUserProjectPermission() {
        List<String> list = new ArrayList<>();
        list.add("working_hour_access");
        authorizationService.UpdateUserProjectPermission("2020-4577-D-02","SYKJ-20200201-0001",list);

    }

    @Test
    void getEditablePermissions() {
        System.out.println(authorizationService.getEditablePermissions());
    }
}