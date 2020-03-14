package com.april.achieveit_project.service;

import com.april.achieveit_project.config.ProjectStateTransition;
import com.april.achieveit_project.entity.Project;
import com.april.achieveit_project.entity.ProjectMiscellaneous;
import com.april.achieveit_project.mapper.ProjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectServiceTest {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProjectService projectService;

    @Test
    @Transactional
    void newProject() {
    Project project = new Project();
    project.setProjectId("2020-4577-D-04");
    project.setStatus(ProjectStateTransition.ProjectState.Applied);
    project.setProjectName("SimpleManage开发");
    project.setReferredOuterCustomerId("CS1123616462");
    project.setScheduledEndTime(new Date(2020-06-01));
    project.setScheduledStartTime(new Date(2020-04-01));
    project.setReferredSuperiorId("SYKJ-20200201-0004");
    project.setMileStone("设计，开发，测试, 交付");
    project.setReferredBusinessFieldId(1234567L);
    project.setTechnology("springboot");
    project.setMainFunction("Manage");
    projectMapper.insert(project);
    }

    @Test
    void searchProjectByName() {
        List<Project> list = projectService.SearchProjectByName("SimpleManage开发",5,1);
        Assert.assertThat(list.get(0).getProjectId(),is("2020-4577-D-03"));
    }

    @Test
    void listRelativeProject() {
        Set<String> projectIds = new HashSet<String>();
        projectIds.add("2020-4577-D-01");
        projectIds.add("2020-4577-D-02");
       List<Project> list = projectService.ListRelativeProject(projectIds,5,1);
       Assert.assertThat(list.get(0).getProjectName(),is("SimpleManage"));
       Assert.assertThat(list.get(1).getProjectName(),is("SimpleManage2"));

    }

    @Test
    void selectByProjectId() {
        Project project =projectService.SelectByProjectId("2020-4577-D-03");
        Assert.assertThat(project.getTechnology(),is("javascript"));
    }

    @Transactional
    @Test
    void updateProjectInfo() {
        Project project = new Project();
        project.setProjectId("2020-4577-D-01");
        project.setStatus(ProjectStateTransition.ProjectState.Applied);
        project.setProjectName("SimpleManage4");
        project.setReferredOuterCustomerId("CS1123616462");
        project.setScheduledEndTime(new Date(2020-06-01));
        project.setScheduledStartTime(new Date(2020-04-01));
        project.setMileStone("设计，开发，测试, 交付");
        project.setReferredBusinessFieldId(7654321L);
        project.setTechnology("springboot");
        project.setMainFunction("Manage");
        projectService.UpdateProjectInfo(project);
    }

    @Test
    void updateProjectStatus() {
        projectService.UpdateProjectStatus("2020-4577-D-01","Initiated");
        Assert.assertThat(projectService.SelectByProjectId("2020-4577-D-01").getStatus(),is("Initiated"));
    }

    @Test
    void updateProjectMiscWhenMemberUpdated() {
       // TODO projectService.UpdateProjectMiscWhenMemberUpdated("");
    }

    @Test
    void selectMiscByProjectId() {
        List<ProjectMiscellaneous> list = projectService.SelectMiscByProjectId("2020-4577-D-02");
        Assert.assertThat(list.get(0).getValueField(),is("edcba"));
        Assert.assertThat(list.get(1).getValueField(),is("ccba"));
    }

    @Test
    void selectMiscByProjectIdAndKey() {
        Assert.assertThat(projectService.SelectMiscByProjectIdAndKey("2020-4577-D-02","abcde"),is("edcba"));
    }

    @Test
    void insertMiscByProjectIdAndKey() {
        projectService.InsertMiscByProjectIdAndKey("2020-4577-D-01","abcd","dcba");
    }
}