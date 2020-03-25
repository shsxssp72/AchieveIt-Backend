package com.april.achieveit_project.service;

import com.april.achieveit_project.config.ProjectStateTransition;
import com.april.achieveit_project.entity.Project;
import com.april.achieveit_project.entity.ProjectMiscellaneous;
import com.april.achieveit_project.mapper.ProjectMapper;
import com.april.achieveit_project.mapper.ProjectMiscellaneousMapper;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectServiceTest {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProjectService projectService;
    @Autowired
    ProjectMiscellaneousMapper projectMiscellaneousMapper;


    @BeforeAll
    public void setUp(){
        Project project = new Project("2020-4577-D-01","Developing","SimpleManage开发","CS1123616462",new Date(2019-12-01),new Date(2020-06-01),"SYKJ-20180101-0000","设计，开发，测试, 交付","springboot",290084263116668929L,"Manage");
        projectMapper.insert(project);
        ProjectMiscellaneous projectMiscellaneous1= new ProjectMiscellaneous(1234L,"2020-4577-D-02","abcde","edcba");
        ProjectMiscellaneous projectMiscellaneous2= new ProjectMiscellaneous(1235L,"2020-4577-D-02","abcc","ccba");
        projectMiscellaneousMapper.insert(projectMiscellaneous1);
        projectMiscellaneousMapper.insert(projectMiscellaneous2);

    }

    @AfterAll
    public void tearDown(){
    projectMapper.deleteByPrimaryKey("2020-4577-D-01");
    projectMiscellaneousMapper.deleteByPrimaryKey(1234L);
    projectMiscellaneousMapper.deleteByPrimaryKey(1235L);
    }

    @Test
    @Transactional
    void newProject() {
    Project project = new Project("2020-4577-D-04","Applied","项目1","CS1123616462",new Date(2020-06-01),new Date(2020-04-01),"SYKJ-20200201-0004","设计，开发","springboot",1234567L,"Manage");
    projectService.NewProject(project);
    Assert.assertThat(projectMapper.selectByPrimaryKey("2020-4577-D-04").getProjectName(),is("项目1"));
    }

    @Test
    void searchProjectByName() {
        List<Project> list = projectService.SearchProjectByName("SimpleManage开发",5,1);
        Assert.assertThat(list.get(0).getProjectId(),is("2020-4577-D-01"));
    }

    @Test
    void selectByProjectIds() {
        Set<String> set = new HashSet<>();
        set.add("2020-4577-D-01");
        List<Project> list= projectService.SelectByProjectIds(set,5,1);
        Assert.assertThat(list.get(0).getProjectName(),is("SimpleManage开发"));

    }

    @Test
    void selectByProjectId() {
        Project project= projectService.SelectByProjectId("2020-4577-D-01");
        Assert.assertThat(project.getProjectName(),is("SimpleManage开发"));

    }



    @Transactional
    @Test
    void updateProjectInfo() {
        Project project = new Project("2020-4577-D-01","Applied","项目1","CS1123616462",new Date(2020-06-01),new Date(2020-04-01),"SYKJ-20200201-0004","设计，开发","springboot",1234567L,"Manage");
        projectService.UpdateProjectInfo(project);
        Assert.assertThat(projectMapper.selectByPrimaryKey("2020-4577-D-01").getProjectName(),is("项目1"));
    }

    @Transactional
    @Test
    void updateProjectStatus() {
        projectService.UpdateProjectStatus("2020-4577-D-01","Delivered");
        Assert.assertThat(projectService.SelectByProjectId("2020-4577-D-01").getStatus(),is("Delivered"));
    }

    @Transactional
    @Test
    void updateProjectMiscWhenMemberUpdated() {
        projectService.UpdateProjectMiscWhenMemberUpdated("2020-4577-D-02","global");
        Assert.assertThat(projectService.SelectMiscByProjectIdAndKey("2020-4577-D-02","global"),is("MemberAdded"));
    }

    @Test
    void selectMiscByProjectIdvAndKeyUsingCache() {
        ProjectMiscellaneous projectMiscellaneous1 = projectService.SelectMiscByProjectIdAndKeyUsingCache("2020-4577-D-02","abcde");
        Assert.assertThat(projectMiscellaneous1.getValueField(),is("edcba"));
        ProjectMiscellaneous projectMiscellaneous2 = projectService.SelectMiscByProjectIdAndKeyUsingCache("2020-4577-D-02","abcc");
        Assert.assertThat(projectMiscellaneous2.getValueField(),is("ccba"));
    }

    @Test
    void selectMiscByProjectIdAndKey() {
        Assert.assertThat(projectService.SelectMiscByProjectIdAndKey("2020-4577-D-02","abcde"),is("edcba"));
    }

    @Transactional
    @Test
    void insertMiscByProjectIdAndKey() {
        projectService.InsertMiscByProjectIdAndKey("2020-4577-D-01","abcd","dcba");
        Assert.assertThat(projectService.SelectMiscByProjectIdAndKey("2020-4577-D-01","abcd"),is("dcba"));
    }
}