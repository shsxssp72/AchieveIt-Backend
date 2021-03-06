package com.april.achieveit_project.service;

import com.april.achieveit_common.utility.RedisCacheUtility;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class ProjectRiskService extends RedisCacheUtility.AbstractRedisCacheService
{
    private static Logger logger=LoggerFactory.getLogger(ProjectRiskService.class);

    static
    {
        for(Method method: ProjectRiskService.class.getDeclaredMethods())
        {

            reentrantLocks.computeIfAbsent(method.getName(),
                                           f->new ReentrantLock());
        }
    }

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${local.cache-valid-time}")
    private Integer cacheValidTime;
    @Value("${local.cache-concurrent-wait-time}")
    private Integer cacheConcurrentWaitTime;

    @Autowired
    private RiskMapper riskMapper;
    @Autowired
    private RiskRelatedPeopleMapper riskRelatedPeopleMapper;


    private List<Risk> selectRiskByProjectId(String projectId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<List<Risk>>(redisTemplate,
                                                                                objectMapper,
                                                                                reentrantLocks.get(currentMethodName),
                                                                                cacheValidTime,
                                                                                cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+projectId;
        Object result=redisCacheHelper.QueryUsingCache(redisKey,
                                                       ()->riskMapper.selectByProjectId(projectId));
        return objectMapper.convertValue(result,
                                         new TypeReference<>()
                                         {
                                         });

    }

    private List<RiskRelatedPeople> selectRiskRelatedPeopleByRiskId(String riskId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<List<RiskRelatedPeople>>(redisTemplate,
                                                                                             objectMapper,
                                                                                             reentrantLocks.get(currentMethodName),
                                                                                             cacheValidTime,
                                                                                             cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+riskId;
        Object result=redisCacheHelper.QueryUsingCache(redisKey,
                                                       ()->riskRelatedPeopleMapper.selectByRiskId(riskId));
        return objectMapper.convertValue(result,
                                         new TypeReference<>()
                                         {
                                         });

    }

    private static String generateNewRiskId()
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd-");
        return "RS-"+simpleDateFormat.format(new Date())+Integer.parseInt(new SimpleDateFormat("HH").format(new Date()))%10+RandomUtils.nextInt(0,
                                                                                                                                                100)+new Date().getTime()%10;
    }

    @Transactional
    public void NewRisk(Risk risk,List<String> relatedPerson)
    {
        String newId=generateNewRiskId();
        for(;riskMapper.selectByPrimaryKey(newId)!=null;)
            newId=generateNewRiskId();
        risk.setRiskId(newId);

        for(String personId: relatedPerson)
        {
            riskRelatedPeopleMapper.insert(new RiskRelatedPeople(newId,
                                                                 personId));
        }
        riskMapper.insert(risk);
    }

    @SneakyThrows
    public List<Map<String,Object>> GetRisk(String projectId)
    {
        List<Map<String,Object>> result=new ArrayList<>();
        List<Risk> risks=selectRiskByProjectId(projectId);
        for(Risk risk: risks)
        {
            List<RiskRelatedPeople> riskRelatedPeople=selectRiskRelatedPeopleByRiskId(risk.getRiskId());
            List<String> people=new ArrayList<>();
            for(RiskRelatedPeople riskRelatedPerson: riskRelatedPeople)
            {
                String referredRelatedPersonId=riskRelatedPerson.getReferredRelatedPersonId();
                people.add(referredRelatedPersonId);
            }
            var riskMap=objectMapper.convertValue(risk,
                                                  new TypeReference<Map<String,Object>>()
                                                  {
                                                  });
            riskMap.put("risk_related_person",
                        people);
            result.add(riskMap);
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
