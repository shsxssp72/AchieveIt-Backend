package com.april.achieveit_project.service;

import com.april.achieveit_project.entity.ProjectFunction;
import com.april.achieveit_project.mapper.ProjectFunctionMapper;
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
class ProjectFunctionServiceTest {

    @Autowired
    ProjectFunctionService projectFunctionService;
    @Autowired
    ProjectFunctionMapper projectFunctionMapper;

    @Transactional
    @Test
    void updateFunctions() {
        Map<String,String> map1 = new HashMap<>();
        map1.put("function_id","1234567");
        map1.put("id_for_display","1");
        map1.put("superior_function_id","7654321");
        map1.put("function_description","功能3");
        Map<String,String> map2= new HashMap<>();
        map2.put("function_id","1234568");
        map2.put("id_for_display","2");
        map2.put("superior_function_id","1234567");
        map2.put("function_description","功能4");
        List<Map<String,String>> functions =new ArrayList<>();
        functions.add(map1);
        functions.add(map2);
        projectFunctionService.UpdateFunctions("2020-4577-D-01",functions);
        Assert.assertThat(projectFunctionMapper.selectByPrimaryKey(1234567L).getDescription(),is("功能3"));
        Assert.assertThat(projectFunctionMapper.selectByPrimaryKey(1234568L).getDescription(),is("功能4"));

    }

    @Test
    void getNewId() {
        System.out.println(projectFunctionService.getNewId());
    }

    @Test
    void getAllProjectFunctions() {
        List<ProjectFunction> list = projectFunctionService.GetAllProjectFunctions("2020-4577-D-01");
        Assert.assertThat(list.get(0).getDescription(),is("功能1"));
        Assert.assertThat(list.get(1).getDescription(),is("功能2"));
    }

    @Test
    void getFunctionCsv() {
        //TODO
    }

    @Test
    void parseFunctionCsv() {
        //TODO
    }
}