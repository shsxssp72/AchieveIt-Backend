package com.april.achieveit_project.service;

import com.april.achieveit_project.entity.ActivityType;
import com.april.achieveit_project.entity.ProjectFunction;
import com.april.achieveit_project.entity.WorkingHour;
import com.april.achieveit_project.mapper.ActivityTypeMapper;
import com.april.achieveit_project.mapper.ProjectFunctionMapper;
import com.april.achieveit_project.mapper.WorkingHourMapper;
import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ProjectWorkingHourServiceTest
{
    @Autowired
    private ProjectWorkingHourService projectWorkingHourService;
    @Autowired
    private WorkingHourMapper workingHourMapper;
    @Autowired
    private ProjectFunctionMapper projectFunctionMapper;
    @Autowired
    private ActivityTypeMapper activityTypeMapper;
    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");


    @SneakyThrows
    @BeforeAll
    public void setUp()
    {
        ProjectFunction projectFunction=new ProjectFunction(290114321369792513L,
                                                            "2020-4577-D-02",
                                                            "001",
                                                            290114321369792513L,
                                                            "界面2");
        WorkingHour workingHour=new WorkingHour(290110995962003457L,
                                                "界面",
                                                "SYKJ-20200201-0000",
                                                "2020-4577-D-01",
                                                290113610967941121L,
                                                290114321369792513L,
                                                dateFormat.parse("2020-01-01"),
                                                dateFormat.parse("2020-01-02"),
                                                false);
        projectFunctionMapper.insert(projectFunction);
        workingHourMapper.insert(workingHour);
        activityTypeMapper.insert(new ActivityType(0L,
                                                   "1",
                                                   "2"));
    }

    @AfterAll
    public void tearDown()
    {
        projectFunctionMapper.deleteByPrimaryKey(290114321369792513L);
        workingHourMapper.deleteByPrimaryKey(290110995962003457L);
        activityTypeMapper.deleteByPrimaryKey(0L);
    }

    @Test
    void assembleWorkingHour()
    {
        Map<String,String> map=new HashMap<>();
        map.put("working_hour_id",
                "290110995962003457");
        map.put("function_description_snapshot",
                "界面");
        WorkingHour workingHour=projectWorkingHourService.assembleWorkingHour("2020-4577-D-01",
                                                                              map);
        MatcherAssert.assertThat(workingHour.getFunctionDescriptionSnapshot(),
                                 is("界面"));
        MatcherAssert.assertThat(workingHour.getWorkingHourId(),
                                 is(290110995962003457L));
    }

    @SneakyThrows
    @Transactional
    @Test
    void newWorkingHour()
    {
        WorkingHour workingHour1=new WorkingHour(290110995962003001L,
                                                 "需求1",
                                                 "SYKJ-20200201-0001",
                                                 "2020-4577-D-01",
                                                 290113610967941001L,
                                                 290114321369792513L,
                                                 dateFormat.parse("2020-01-01"),
                                                 dateFormat.parse("2020-01-02"),
                                                 false);
        projectWorkingHourService.NewWorkingHour(workingHour1);
        MatcherAssert.assertThat(projectWorkingHourService.SelectByProjectIdAndUserId("2020-4577-D-01",
                                                                                      "SYKJ-20200201-0001")
                                         .get(0)
                                         .getFunctionDescriptionSnapshot(),
                                 is("界面2"));

    }

    @Test
    void SelectByProjectIdAndUserId()
    {
        List<WorkingHour> list=projectWorkingHourService.SelectByProjectIdAndUserId("2020-4577-D-01",
                                                                                    "SYKJ-20200201-0000");
        MatcherAssert.assertThat(list.get(0)
                                         .getFunctionDescriptionSnapshot(),
                                 is("界面"));
    }

    @Test
    void selectByWorkingHourId()
    {
        WorkingHour workingHour=projectWorkingHourService.SelectByWorkingHourId(290110995962003457L,
                                                                                "SYKJ-20200201-0000");
        MatcherAssert.assertThat(workingHour.getFunctionDescriptionSnapshot(),
                                 is("界面"));
    }

    @Test
    void selectProjectFunctionByPrimaryKey()
    {
        MatcherAssert.assertThat(projectWorkingHourService.selectProjectFunctionByPrimaryKey(290114321369792513L)
                                         .getDescription(),
                                 is("界面2"));

    }

    @SneakyThrows
    @Transactional
    @Test
    void updateWorkingHour()
    {
        WorkingHour workingHour=new WorkingHour(290110995962003457L,
                                                "需求2",
                                                "SYKJ-20200201-0000",
                                                "2020-4577-D-01",
                                                290113610967941002L,
                                                290114321369792513L,
                                                dateFormat.parse("2020-01-01"),
                                                dateFormat.parse("2020-01-02"),
                                                false);
        projectWorkingHourService.UpdateWorkingHour(workingHour,
                                                    "SYKJ-20200201-0000");
        MatcherAssert.assertThat(projectWorkingHourService.SelectByWorkingHourId(290110995962003457L,
                                                                                 "SYKJ-20200201-0000")
                                         .getFunctionDescriptionSnapshot(),
                                 is("界面2"));
    }

    @Test
    void verifyWorkingHour()
    {
               projectWorkingHourService.VerifyWorkingHour(290110995962003457L,true,"SYKJ-20200201-1111");
               MatcherAssert.assertThat(projectWorkingHourService.SelectByWorkingHourId(290110995962003457L,"SYKJ-20200201-0000").getVerified(),is(true));
    }

    @Test
    void getToBeVerifiedWorkingHour()
    {
                List<WorkingHour> list = projectWorkingHourService.GetToBeVerifiedWorkingHour("2020-4577-D-01","SYKJ-20200201-1111");
                System.out.println(list);
    }

    @Test
    void listAllActivityType()
    {
        MatcherAssert.assertThat(projectWorkingHourService.ListAllActivityType()
                                         .size(),
                                 is(1));
    }
}