package com.april.achieveit_project.service;

import com.april.achieveit_project.entity.Risk;
import com.april.achieveit_project.mapper.RiskMapper;
import com.april.achieveit_project.mapper.RiskRelatedPeopleMapper;
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
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectRiskServiceTest {

    @Autowired ProjectRiskService projectRiskService;
    @Autowired
    private RiskMapper riskMapper;
    @Autowired
    private RiskRelatedPeopleMapper riskRelatedPeopleMapper;

    @BeforeAll
    public void setUp(){
       Risk risk = new Risk("RS-20200101-0000","2020-4577-D-01","PS","需求变更","H","H","沟通","已识别","每周1次");
       riskMapper.insert(risk);

    }

    @AfterAll
    public void tearDown(){
        riskMapper.deleteByPrimaryKey("RS-20200101-0000");
    }

    @Transactional
    @Test
    void newRisk() {
       Risk risk = new Risk("RS-20200101-0001","2020-4577-D-02","PS","需求变更2","H","H","沟通","已识别","每周一次");
        List<String> list = new ArrayList<>();
        list.add("SYKJ-20200201-0001");
        list.add("SYKJ-20200201-0002");
        list.add("SYKJ-20200201-0003");
        projectRiskService.NewRisk(risk,list);
        Assert.assertThat(riskMapper.selectByProjectId("2020-4577-D-02").get(0).getRiskDescription(),is("需求变更2"));
        //Assert.assertThat(riskRelatedPeopleMapper.selectByRiskId("RS-20200101-0001").get(0).getReferredRelatedPersonId(),is("SYKJ-20200201-0001"));
        //RiskId随机生成
    }

    @Test
    void getRisk() {
        List<String> list = projectRiskService.GetRisk("2020-4577-D-01");
        System.out.println(list.get(0));
        Assert.assertThat(list.get(0).substring(0,30),is("{\"risk_id\":\"RS-20200101-0000\","));

    }

    @Transactional
    @Test
    void updateRisk() {
        Risk risk = new Risk("RS-20200101-0000","2020-4577-D-03","PS","需求变更3","H","H","沟通","已识别","每周一次");
        List<String> list =new ArrayList<>();
        list.add("SYKJ-20200201-0004");
        projectRiskService.UpdateRisk(risk,list);
        Assert.assertThat(riskMapper.selectByProjectId("2020-4577-D-03").get(0).getRiskDescription(),is("需求变更3"));
        Assert.assertThat(riskRelatedPeopleMapper.selectByRiskId("RS-20200101-0000").get(0).getReferredRelatedPersonId(),is("SYKJ-20200201-0004"));



    }
}