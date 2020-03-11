package com.april.achieveit_project.service;

import com.april.achieveit_project.controller.ProjectRiskController;
import com.april.achieveit_project.entity.Risk;
import com.april.achieveit_project.entity.RiskRelatedPeople;
import com.april.achieveit_project.mapper.RiskMapper;
import com.april.achieveit_project.mapper.RiskRelatedPeopleMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProjectRiskService
{
    private static Logger logger=LoggerFactory.getLogger(ProjectRiskController.class);
    @Autowired
    private RiskMapper riskMapper;
    @Autowired
    private RiskRelatedPeopleMapper riskRelatedPeopleMapper;
    @Autowired
    private ObjectMapper objectMapper;


    private static String generateNewRiskId()
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd-");
        return "RS-"+simpleDateFormat.format(new Date())+Integer.parseInt(new SimpleDateFormat("HH").format(new Date()))%10+RandomUtils.nextInt(0,
                                                                                                                                                100)+new Date().getTime()%10;
    }

    public void NewRisk(Risk risk,List<String> relatedPerson)
    {
        String newId=generateNewRiskId();
        for(;riskMapper.selectByPrimaryKey(newId)!=null;)
            newId=generateNewRiskId();
        risk.setReferredProjectId(newId);

        for(String personId: relatedPerson)
        {
            riskRelatedPeopleMapper.insert(new RiskRelatedPeople(newId,
                                                                 personId));
        }
        riskMapper.insert(risk);
    }

    @SneakyThrows
    public List<String> GetRisk(String projectId)
    {
        List<String> result=new ArrayList<>();
        List<Risk> risks=riskMapper.selectByProjectId(projectId);
        for(Risk risk:risks)
        {
            List<RiskRelatedPeople> riskRelatedPeople=riskRelatedPeopleMapper.selectByRiskId(risk.getRiskId());
            List<String> people=riskRelatedPeople.stream().map(RiskRelatedPeople::getReferredRelatedPersonId).collect(Collectors.toList());
            var riskMap=objectMapper.convertValue(risk,
                                      new TypeReference<Map<String,String>>()
                                      {
                                      });
            riskMap.put("risk_responsible_person",objectMapper.writeValueAsString(people));
            result.add(objectMapper.writeValueAsString(riskMap));
        }
        return result;
    }

    public void UpdateRisk(Risk risk,List<String> relatedPerson)
    {
        riskRelatedPeopleMapper.deleteByRiskId(risk.getRiskId());
        for(String personId: relatedPerson)
        {
            riskRelatedPeopleMapper.insert(new RiskRelatedPeople(risk.getRiskId(),
                                                                 personId));
        }
        riskMapper.updateByPrimaryKeySelective(risk);
    }

}
