package com.april.achieveit_project.service;

import com.april.achieveit_project.entity.ProjectFunction;
import com.april.achieveit_project.mapper.ProjectFunctionMapper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ProjectFunctionServiceTest
{

    @Autowired
    private ProjectFunctionService projectFunctionService;
    @Autowired
    private ProjectFunctionMapper projectFunctionMapper;
    @Autowired
    private ProjectWorkingHourService projectWorkingHourService;

    @BeforeAll
    public void setUp()
    {
        ProjectFunction projectFunction=new ProjectFunction(290114321369792513L,
                                                            "2020-4577-D-02",
                                                            "001",
                                                            290114321369792513L,
                                                            "界面");
        ProjectFunction projectFunction1=new ProjectFunction(290114321369792123L,
                                                             "2020-4577-D-02",
                                                             "002",
                                                             290114321369792123L,
                                                             "界面5");

        projectFunctionMapper.insert(projectFunction);
        projectFunctionMapper.insert(projectFunction1);
    }

    @AfterAll
    public void tearDown()
    {
        projectFunctionMapper.deleteByPrimaryKey(290114321369792513L);
        projectFunctionMapper.deleteByPrimaryKey(290114321369792123L);


    }

    @Transactional
    @Test
    void updateFunctions()
    {
        Map<String,String> map1=new HashMap<>();
        map1.put("function_id",
                 "290114321369792001");
        map1.put("id_for_display",
                 "001");
        map1.put("superior_display_id",
                 "001");
        map1.put("function_description",
                 "界面2");
        Map<String,String> map2=new HashMap<>();
        map2.put("function_id",
                 "290114321369792002");
        map2.put("id_for_display",
                 "002");
        map2.put("superior_display_id",
                 "002");
        map2.put("function_description",
                 "界面3");
        List<Map<String,String>> functions=new ArrayList<>();
        functions.add(map1);
        functions.add(map2);
        projectFunctionService.UpdateFunctions("2020-4577-D-02",
                                               functions);
        System.out.println(projectFunctionService.GetAllProjectFunctions("2020-4577-D-02"));
        MatcherAssert.assertThat(projectFunctionService.GetAllProjectFunctions("2020-4577-D-02")
                                         .get(0)
                                         .get("function_description"),
                                 is("界面2"));
        MatcherAssert.assertThat(projectFunctionService.GetAllProjectFunctions("2020-4577-D-02")
                                         .get(1)
                                         .get("function_description"),
                                 is("界面3"));

    }

    @Test
    void getNewId()
    {
        System.out.println(projectFunctionService.getNewId()
                                   .toString()
                                   .length());
        MatcherAssert.assertThat(projectFunctionService.getNewId()
                                         .toString()
                                         .length(),
                                 is(18));
    }

    @Test
    void ClassifyFunctionByIsSuperior()
    {
        ImmutablePair<List<Map<String,String>>,List<Map<String,String>>> immutablePair=projectFunctionService.ClassifyFunctionByIsSuperior("2020-4577-D-02");
        System.out.println(immutablePair.getLeft());
        MatcherAssert.assertThat(immutablePair.getLeft()
                                         .get(0)
                                         .get("function_description"),
                                 is("界面"));
        MatcherAssert.assertThat(immutablePair.getLeft()
                                         .get(1)
                                         .get("function_description"),
                                 is("界面5"));

    }

    @Test
    void getAllProjectFunctions()
    {
        List<Map<String,String>> list=projectFunctionService.GetAllProjectFunctions("2020-4577-D-02");
        System.out.println(list);
        MatcherAssert.assertThat(list.get(0)
                                         .get("function_description"),
                                 is("界面"));
        MatcherAssert.assertThat(list.get(1)
                                         .get("function_description"),
                                 is("界面5"));

    }


    @Test
    void getFunctionCsv()
    {
        System.out.println(projectFunctionService.GetFunctionCsv("2020-4577-D-02"));
        //       MatcherAssert.assertThat(projectFunctionService.GetFunctionCsv("2020-4577-D-02"),is("function_id,superior_function_id,function_description\\r\\n001,001,界面\\r\\n002,002,界面5\\r\\n"));
    }

    @Test
    void parseFunctionCsv()
    {
        System.out.println(projectFunctionService.ParseFunctionCsv("2020-4577-D-02",
                                                                   "function_id,superior_function_id,function_description\\r\\n001,001,界面\\r\\n002,002,界面5\\r\\n"));

    }


}