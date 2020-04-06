package com.april.achieveit_project.service;

import com.april.achieveit_project.entity.Risk;
import com.april.achieveit_project.mapper.RiskMapper;
import com.april.achieveit_project.mapper.RiskRelatedPeopleMapper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ProjectRiskServiceTest
{

    @Autowired
    private ProjectRiskService projectRiskService;
    @Autowired
    private RiskMapper riskMapper;
    @Autowired
    private RiskRelatedPeopleMapper riskRelatedPeopleMapper;

    @BeforeAll
    public void setUp()
    {
        Risk risk=new Risk("RS-20200101-0000",
                           "2020-4577-D-01",
                           "PS",
                           "需求变更",
                           "H",
                           "H",
                           "沟通",
                           "已识别",
                           null,
                           "每周1次");
        riskMapper.insert(risk);

    }

    @AfterAll
    public void tearDown()
    {
        riskMapper.deleteByPrimaryKey("RS-20200101-0000");
    }

    @Transactional
    @Test
    void newRisk()
    {
        Risk risk=new Risk("RS-20200101-0001",
                           "2020-4577-D-02",
                           "PS",
                           "需求变更2",
                           "H",
                           "H",
                           "沟通",
                           "已识别",
                           null,
                           "每周一次");
        List<String> list=new ArrayList<>();
        list.add("SYKJ-20200201-0001");
        list.add("SYKJ-20200201-0002");
        list.add("SYKJ-20200201-0003");
        projectRiskService.NewRisk(risk,
                                   list);
        MatcherAssert.assertThat(riskMapper.selectByProjectId("2020-4577-D-02")
                                         .get(0)
                                         .getRiskDescription(),
                                 is("需求变更2"));
        //MatcherAssert.assertThat(riskRelatedPeopleMapper.selectByRiskId("RS-20200101-0001").get(0).getReferredRelatedPersonId(),is("SYKJ-20200201-0001"));
        //RiskId随机生成
    }

    @Test
    void getRisk()
    {
        List<Map<String,Object>> list=projectRiskService.GetRisk("2020-4577-D-01");
        System.out.println(list.get(0));

    }

    @Transactional
    @Test
    void updateRisk()
    {
        Risk risk=new Risk("RS-20200101-0000",
                           "2020-4577-D-03",
                           "PS",
                           "需求变更3",
                           "H",
                           "H",
                           "沟通",
                           "已识别",
                           null,
                           "每周一次");
        List<String> list=new ArrayList<>();
        list.add("SYKJ-20200201-0004");
        projectRiskService.UpdateRisk(risk,
                                      list);
        MatcherAssert.assertThat(riskMapper.selectByProjectId("2020-4577-D-03")
                                         .get(0)
                                         .getRiskDescription(),
                                 is("需求变更3"));
        MatcherAssert.assertThat(riskRelatedPeopleMapper.selectByRiskId("RS-20200101-0000")
                                         .get(0)
                                         .getReferredRelatedPersonId(),
                                 is("SYKJ-20200201-0004"));


    }
}