package com.april.achieveit_project.service;

import com.april.achieveit_project.entity.WorkingHour;
import com.april.achieveit_project.mapper.WorkingHourMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectWorkingHourServiceTest {
@Autowired ProjectWorkingHourService projectWorkingHourService;
    @Autowired
    private WorkingHourMapper workingHourMapper;

    @Transactional
    @Test
    void newWorkingHour() {
        WorkingHour workingHour= new WorkingHour(2L,"需求1","SYKJ-20200201-0001","2020-4577-D-01",2L,3L,new Date(2020-01-01),new Date(2020-01-02),false);
        projectWorkingHourService.NewWorkingHour(workingHour);
        Assert.assertThat(projectWorkingHourService.SelectByWorkingHourId(2L,"SYKJ-20200201-0001").getFunctionDescriptionSnapshot(),is("需求1"));

    }

    @Test
    void selectByProjectId() {
        List<WorkingHour> list = projectWorkingHourService.SelectByProjectId("2020-4577-D-01","SYKJ-20200201-0001");
        Assert.assertThat(list.get(0).getFunctionDescriptionSnapshot(),is("需求1"));
    }

    @Test
    void selectByWorkingHourId() {
        WorkingHour workingHour = projectWorkingHourService.SelectByWorkingHourId(1L,"SYKJ-20200201-0001");
        Assert.assertThat(workingHour.getFunctionDescriptionSnapshot(),is("需求1"));
    }

    @Transactional
    @Test
    void updateWorkingHour() {
        WorkingHour workingHour = new WorkingHour(1L,"需求2","SYKJ-20200201-0001","2020-4577-D-01",2L,3L,new Date(2020-01-01),new Date(2020-01-02),false);
        projectWorkingHourService.UpdateWorkingHour(workingHour,"SYKJ-20200201-0001");
        Assert.assertThat(projectWorkingHourService.SelectByWorkingHourId(1L,"SYKJ-20200201-0001").getFunctionDescriptionSnapshot(),is("需求2"));
    }

    @Test
    void verifyWorkingHour() {
        //TODO
    }

    @Test
    void getToBeVerifiedWorkingHour() {
        //TODO
    }

    @Test
    void listAllActivityType() {
        //TODO
    }
}