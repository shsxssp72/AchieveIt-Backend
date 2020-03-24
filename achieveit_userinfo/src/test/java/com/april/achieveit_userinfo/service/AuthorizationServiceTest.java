package com.april.achieveit_userinfo.service;

import com.april.achieveit_userinfo.mapper.*;
import com.april.achieveit_userinfo_interface.entity.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
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
    @Autowired
    ProjectUserPermissionRelationMapper projectUserPermissionRelationMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    GlobalRoleMapper globalRoleMapper;

    @BeforeAll
    public void setup(){
        ProjectUserRelation projectUserRelation1 = new ProjectUserRelation("2020-4577-D-01","SYKJ-20200201-0000","SYKJ-20200101-0000",290089467161608193L);
        ProjectUserRelation projectUserRelation2 = new ProjectUserRelation("2020-4577-D-02","SYKJ-20200201-0001","SYKJ-20200101-0010",290089467161608123L);
        ProjectUserRelation projectUserRelation3 = new ProjectUserRelation("2020-4577-D-02","SYKJ-20200201-0003","SYKJ-20200101-0010",290089467161608125L);

        projectUserRelationMapper.insert(projectUserRelation1);
        projectUserRelationMapper.insert(projectUserRelation2);
        projectUserRelationMapper.insert(projectUserRelation3);

        ProjectRole projectRole1 = new ProjectRole(290089467161608123L,"QaLeader");
        ProjectRole projectRole2 = new ProjectRole(290089467161608125L,"EPG");
        ProjectRole projectRole3 = new ProjectRole(290089467161608193L,"DevelopmentLeader");
        projectRoleMapper.insert(projectRole1);
        projectRoleMapper.insert(projectRole2);
        projectRoleMapper.insert(projectRole3);

        UserInfo userInfo = new UserInfo("SYKJ-20200201-0001",287991808128974852L,"mpan@hotmail.com","466071c6546622410622052f2b285dd47e933021dfe1e9576a4d8bfa2abe85f20bdbe269a306e50d5bc671ea19bbc1e317abfa0539b2d148dd875ca6de0be645","nrRPQXyABflAbUlJOlRGByAZHrjgxspLwrqyRmSnFCOuOcGaCjpGXcxAdeJfNgio",290104426675306497L);
        userInfoMapper.insert(userInfo);

        ProjectUserPermissionRelation projectUserPermissionRelation = new ProjectUserPermissionRelation("2020-4577-D-02","SYKJ-20200201-0001",290089467161608123L,5);
        ProjectUserPermissionRelation projectUserPermissionRelation2 = new ProjectUserPermissionRelation("2020-4577-D-01","SYKJ-20200201-0000",294208653534167040L,1);
        projectUserPermissionRelationMapper.insert(projectUserPermissionRelation);
        projectUserPermissionRelationMapper.insert(projectUserPermissionRelation2);

        Permission permission1=new Permission(290089467161608123L,"issue_tracker_access");
        Permission permission2=new Permission(294208701688971264L,"issue_tracker_modification");
        Permission permission3=new Permission(294208708563435520L,"working_hour_modification");
        Permission permission4=new Permission(294208716280954880L,"working_hour_access");
        Permission permission5=new Permission(294208717023346688L,"working_hour_verification");
        Permission permission6=new Permission(294208731082653696L,"project_git_modification");
        Permission permission7=new Permission(294208732491939840L,"project_git_access");
        Permission permission8=new Permission(294208733922197504L,"mail_list_modification");
        Permission permission9=new Permission(294208740175904768L,"mail_list_access");
        Permission permission10=new Permission(294208748191219712L,"file_system_modification");
        Permission permission11=new Permission(294208755745161216L,"file_system_access");
        permissionMapper.insert(permission1);
        permissionMapper.insert(permission2);
        permissionMapper.insert(permission3);
        permissionMapper.insert(permission4);
        permissionMapper.insert(permission5);
        permissionMapper.insert(permission6);
        permissionMapper.insert(permission7);
        permissionMapper.insert(permission8);
        permissionMapper.insert(permission9);
        permissionMapper.insert(permission10);
        permissionMapper.insert(permission11);

        GlobalRole globalRole= new GlobalRole(290104426675306497L,"ConfigurationManager");
        globalRoleMapper.insert(globalRole);

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
        List<String> list= authorizationService.GetInferior("2020-4577-D-02","SYKJ-20200101-0010");
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

        map.put("superior_id","SYKJ-20200201-0011");
        map.put("project_role_id","290089467161608193");

        List<Map<String,String>> list =new ArrayList<>();
        list.add(map);
        authorizationService.UpdateUserProjectRole("2020-4577-D-02","SYKJ-20200201-00001",list);
    //   Assert.assertThat(authorizationService.GetUserProjectRole("2020-4577-D-02","SYKJ-20200201-00001").get("superioor_id"),is("SYKJ-20200201-0011"));
    }

    @Transactional
    @Test
    void updateProjectMember() {
        Map<String,String> map=new HashMap<>();
        map.put("user_id","SYKJ-20200201-0004");
        map.put("project_role_id_list","[{\"project_role_id\":\"126\",\"superior_id\":\"SYKJ-20200201-0010\",\"project_role_name\":\"QaLeader\"}]");
        List<Map<String,String>> members = new ArrayList<>();
        members.add(map);
        authorizationService.UpdateProjectMember("2020-4577-D-02",members);
      //  Assert.assertThat(authorizationService.GetProjectMember("2020-4577-D-02").get(0).get("project_role_name"),is("QaLeader"));
}

    @Transactional
    @Test
    void updateUserProjectPermission() {
        List<String> list = new ArrayList<>();
        list.add("working_hour_access");
        authorizationService.UpdateUserProjectPermission("2020-4577-D-02","SYKJ-20200201-0001",list);
        String s=authorizationService.GetUserPermissionName("2020-4577-D-02","SYKJ-20200201-0001").get(0);
        Assert.assertThat(s,is("working_hour_access"));

    }

    @Test
    void getEditablePermissions() {
        System.out.println(authorizationService.getEditablePermissions());
    }

    @AfterAll
    public void teardown(){
        projectUserRelationMapper.deleteByProjectIdAndUserId("2020-4577-D-01","SYKJ-20200201-0000");
        projectUserRelationMapper.deleteByProjectIdAndUserId("2020-4577-D-02","SYKJ-20200201-0001");
        projectUserRelationMapper.deleteByProjectIdAndUserId("2020-4577-D-02","SYKJ-20200201-0003");

        projectRoleMapper.deleteByPrimaryKey(290089467161608123L);
        projectRoleMapper.deleteByPrimaryKey(290089467161608125L);
        projectRoleMapper.deleteByPrimaryKey(290089467161608193L);

        userInfoMapper.deleteByPrimaryKey("SYKJ-20200201-0001");

        projectUserPermissionRelationMapper.deleteByProjectIdAndUserIdAndPermissionId("2020-4577-D-02","SYKJ-20200201-0001",290089467161608123L);
        projectUserPermissionRelationMapper.deleteByProjectIdAndUserIdAndPermissionId("2020-4577-D-01","SYKJ-20200201-0000",294208653534167040L);

        permissionMapper.deleteByPrimaryKey(290089467161608123L);
        permissionMapper.deleteByPrimaryKey(294208701688971264L);
        permissionMapper.deleteByPrimaryKey(294208708563435520L);
        permissionMapper.deleteByPrimaryKey(294208716280954880L);
        permissionMapper.deleteByPrimaryKey(294208717023346688L);
        permissionMapper.deleteByPrimaryKey(294208731082653696L);
        permissionMapper.deleteByPrimaryKey(294208732491939840L);
        permissionMapper.deleteByPrimaryKey(294208733922197504L);
        permissionMapper.deleteByPrimaryKey(294208740175904768L);
        permissionMapper.deleteByPrimaryKey(294208748191219712L);
        permissionMapper.deleteByPrimaryKey(294208755745161216L);

        globalRoleMapper.deleteByPrimaryKey(290104426675306497L);





    }

}