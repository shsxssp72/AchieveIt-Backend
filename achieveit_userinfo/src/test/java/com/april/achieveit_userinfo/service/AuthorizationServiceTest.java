package com.april.achieveit_userinfo.service;

import com.april.achieveit_userinfo.mapper.ProjectRoleMapper;
import com.april.achieveit_userinfo.mapper.ProjectUserRelationMapper;
import com.april.achieveit_userinfo.mapper.UserInfoMapper;
import com.april.achieveit_userinfo_interface.entity.ProjectRole;
import com.april.achieveit_userinfo_interface.entity.ProjectUserRelation;
import com.april.achieveit_userinfo_interface.entity.UserInfo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthorizationServiceTest {
    @Autowired AuthorizationService authorizationService;
    @Autowired
    ProjectUserRelationMapper projectUserRelationMapper;
    @Autowired
    ProjectRoleMapper projectRoleMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    @BeforeAll
    public void setup(){
//        ProjectUserRelation projectUserRelation1 = new ProjectUserRelation("2020-4577-D-01","SYKJ-20200201-0000","SYKJ-20200101-0000",290089467161608193L);
//        ProjectUserRelation projectUserRelation2 = new ProjectUserRelation("2020-4577-D-02","SYKJ-20200201-0001","SYKJ-20200101-0010",290089467161608123L);
//        ProjectUserRelation projectUserRelation3 = new ProjectUserRelation("2020-4577-D-02","SYKJ-20200201-0003","SYKJ-20200101-0010",290089467161608125L);
//
//        projectUserRelationMapper.insert(projectUserRelation1);
//        projectUserRelationMapper.insert(projectUserRelation2);
//        projectUserRelationMapper.insert(projectUserRelation3);
//        ProjectRole projectRole1 = new ProjectRole(290089467161608123L,"QaLeader");
//        ProjectRole projectRole2 = new ProjectRole(290089467161608125L,"EPG");
//        projectRoleMapper.insert(projectRole1);
//        projectRoleMapper.insert(projectRole2);
//        UserInfo userInfo = new UserInfo("SYKJ-20200201-0001",287991808128974852L,"mpan@hotmail.com","466071c6546622410622052f2b285dd47e933021dfe1e9576a4d8bfa2abe85f20bdbe269a306e50d5bc671ea19bbc1e317abfa0539b2d148dd875ca6de0be645","nrRPQXyABflAbUlJOlRGByAZHrjgxspLwrqyRmSnFCOuOcGaCjpGXcxAdeJfNgio",290104426675306497L);
//        userInfoMapper.insert(userInfo);


        System.out.println("setup");

    }

    @Test
    void getUserProjectRole() {
        Assert.assertThat(authorizationService.GetUserProjectRole("2020-4577-D-01","SYKJ-20200201-0000").get("project_id"),is("2020-4577-D-01"));

    }

    @Test
    void getUserRoleFromMultipleProject() {
        List<Map<String,String>> list =authorizationService.GetUserRoleFromMultipleProject("SYKJ-20200201-0000");
        Assert.assertThat(list.get(0).get("project_id"),is("2020-4577-D-01"));
    }

    @Test
    void batchGetUserRoleFromMultipleProject() {
        List<String> list = new ArrayList<>();
        list.add("SYKJ-20200201-0000");
        list.add("SYKJ-20200201-0001");
        List<Map<String,String>> resultList =authorizationService.BatchGetUserRoleFromMultipleProject(list);
        System.out.println(authorizationService.BatchGetUserRoleFromMultipleProject(list));
        Assert.assertThat(resultList.get(0).get("project_id"),is("2020-4577-D-01"));
        Assert.assertThat(resultList.get(1).get("project_id"),is("2020-4577-D-02"));
    }

    @Test
    void getProjectMember() {
        List<Map<String,String>> list =authorizationService.GetProjectMember("2020-4577-D-02");
        System.out.println(list);
        Assert.assertThat(list.get(0).get("project_role_name"),is("QaLeader"));
        Assert.assertThat(list.get(1).get("project_role_name"),is("EPG"));
    }

    @Test
    void getUserGlobalRole() {
        Map<String,String> map=authorizationService.GetUserGlobalRole("SYKJ-20200201-0001");
        Assert.assertThat(map.get("global_role_name"),is("ConfigurationManager"));
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

    @Test
    void updateUserProjectRole() {
        Map<String,String> map=new HashMap<>();
        map.put("project_role_name","EPG");
        map.put("superior_id","SYKJ-20200201-0010");
        map.put("project_role_id","290089467161608193");

        List<Map<String,String>> list =new ArrayList<>();
        list.add(map);
       authorizationService.UpdateUserProjectRole("2020-4577-D-01","SYKJ-20200201-0000",list);
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
        authorizationService.UpdateUserProjectPermission("2020-4577-D-01","SYKJ-20200201-0000",list);

    }

    @Test
    void getEditablePermissions() {
        System.out.println(authorizationService.getEditablePermissions());
    }
}