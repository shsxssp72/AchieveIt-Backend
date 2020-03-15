package com.april.achieveit_project.service;

import com.april.achieveit_project.entity.Risk;
import com.april.achieveit_project.mapper.RiskMapper;
import com.april.achieveit_project.mapper.RiskRelatedPeopleMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectRiskServiceTest {

    @Autowired ProjectRiskService projectRiskService;
    @Autowired
    private RiskMapper riskMapper;
    @Autowired
    private RiskRelatedPeopleMapper riskRelatedPeopleMapper;

    @Transactional
    @Test
    void newRisk() {
        Risk risk = new Risk("riskid","2020-4577-D-02","Type","需求变更","1级","impact","沟通","Status","SYKJ-20200201-0000","TF");
        List<String> list = new ArrayList<>();
        list.add("SYKJ-20200201-0001");
        list.add("SYKJ-20200201-0002");
        list.add("SYKJ-20200201-0003");
        projectRiskService.NewRisk(risk,list);
        Assert.assertThat(riskMapper.selectByProjectId("2020-4577-D-02").get(0).getRiskDescription(),is("需求变更"));
    }

    @Test
    void getRisk() {
        List<String> list = projectRiskService.GetRisk("2020-4577-D-01");
        for (String string :list)
        {
            System.out.println(string);
        }
    }

    @Transactional
    @Test
    void updateRisk() {
        Risk risk = new Risk("RS-20200315-2967","2020-4577-D-01","Type","需求变更2","1级","impact","沟通","Status","SYKJ-20200201-0000","TF");
        List<String> list =new ArrayList<>();
        list.add("SYKJ-20200201-0004");
        projectRiskService.UpdateRisk(risk,list);
        Assert.assertThat(riskMapper.selectByProjectId("2020-4577-D-01").get(0).getRiskDescription(),is("需求变更2"));
        Assert.assertThat(riskRelatedPeopleMapper.selectByRiskId("RS-20200315-2967").get(0).getReferredRelatedPersonId(),is("SYKJ-20200201-0004"));



    }
}