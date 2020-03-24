package com.april.achieveit_project.service;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.utility.RedisCacheUtility;
import com.april.achieveit_common.utility.SnowFlakeIdGenerator;
import com.april.achieveit_project.client.RoleServiceClient;
import com.april.achieveit_project.entity.ActivityType;
import com.april.achieveit_project.entity.ProjectFunction;
import com.april.achieveit_project.entity.WorkingHour;
import com.april.achieveit_project.mapper.ActivityTypeMapper;
import com.april.achieveit_project.mapper.ProjectFunctionMapper;
import com.april.achieveit_project.mapper.WorkingHourMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProjectWorkingHourService extends RedisCacheUtility.AbstractRedisCacheService
{
    private static Logger logger=LoggerFactory.getLogger(ProjectWorkingHourService.class);

    static
    {
        for(Method method: ProjectWorkingHourService.class.getDeclaredMethods())
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
    private WorkingHourMapper workingHourMapper;
    @Autowired
    private ActivityTypeMapper activityTypeMapper;
    @Autowired
    private RoleServiceClient roleServiceClient;
    @Autowired
    ProjectFunctionMapper projectFunctionMapper;


    @Value("${snowflake.datacenter-id}")
    private Long datacenterId;
    @Value("${snowflake.machine-id}")
    private Long machineId;
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    @PostConstruct
    private void init()
    {
        snowFlakeIdGenerator=new SnowFlakeIdGenerator(datacenterId,
                                                      machineId);
    }

    public void NewWorkingHour(WorkingHour workingHour)
    {
        workingHour.setReferredFunctionId(snowFlakeIdGenerator.getNextId());
        ProjectFunction referredWorkingHour=projectFunctionMapper.selectByPrimaryKey(workingHour.getReferredFunctionId());
        workingHour.setFunctionDescriptionSnapshot(referredWorkingHour.getDescription());
        workingHourMapper.insert(workingHour);
    }

    public List<WorkingHour> SelectByProjectIdAndUserId(String projectId,String userId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<List<WorkingHour>>(redisTemplate,
                                                                                       objectMapper,
                                                                                       reentrantLocks.get(currentMethodName),
                                                                                       cacheValidTime,
                                                                                       cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+projectId+"_"+userId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->workingHourMapper.selectByProjectIdAndUserIds(projectId,
                                                                                                  List.of(userId)));
    }

    private WorkingHour selectWorkingHourByPrimaryKey(Long workingHourId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<WorkingHour>(redisTemplate,
                                                                                 objectMapper,
                                                                                 reentrantLocks.get(currentMethodName),
                                                                                 cacheValidTime,
                                                                                 cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+workingHourId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->workingHourMapper.selectByPrimaryKey(workingHourId));
    }

    public WorkingHour SelectByWorkingHourId(Long workingHourId,String userId)
    {
        WorkingHour queryResult=selectWorkingHourByPrimaryKey(workingHourId);
        if(!queryResult.getReferredUserId()
                .equals(userId))
            throw new IllegalArgumentException("User can only view his own working hour");
        return queryResult;
    }

    public ProjectFunction selectProjectFunctionByPrimaryKey(Long functionId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<ProjectFunction>(redisTemplate,
                                                                                     objectMapper,
                                                                                     reentrantLocks.get(currentMethodName),
                                                                                     cacheValidTime,
                                                                                     cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+functionId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->projectFunctionMapper.selectByPrimaryKey(functionId));
    }

    public void UpdateWorkingHour(WorkingHour workingHour,String userId)
    {
        Long workingHourId=workingHour.getWorkingHourId();
        WorkingHour currentWorkingHour=selectWorkingHourByPrimaryKey(workingHourId);
        if(!currentWorkingHour.getReferredUserId()
                .equals(userId))
            throw new IllegalArgumentException("User can only update his own working hour");
        workingHour.setVerified(false);
        ProjectFunction referredWorkingHour=selectProjectFunctionByPrimaryKey(workingHour.getReferredFunctionId());
        workingHour.setFunctionDescriptionSnapshot(referredWorkingHour.getDescription());
        workingHourMapper.updateByPrimaryKeySelective(workingHour);
    }

    public void VerifyWorkingHour(Long workingHourId,Boolean verifyResult,String verifierId)
    {
        WorkingHour currentWorkingHour=selectWorkingHourByPrimaryKey(workingHourId);
        String creatorId=currentWorkingHour.getReferredProjectId();
        String projectId=currentWorkingHour.getReferredProjectId();
        //TODO role controller api may change, confirm in the end
        ResponseContent queryResponse=roleServiceClient.GetUserProjectRole(new HashMap<>()
        {{
            put("project_id",
                projectId);
            put("user_id",
                creatorId);
        }});
        List<Map<String,String>> queryResult=objectMapper.convertValue(queryResponse.getResult(),
                                                                       new TypeReference<List<Map<String,String>>>()
                                                                       {
                                                                       });
        var currentProjectRole=queryResult.get(0);
        if(!currentProjectRole.get("superior_id")
                .equals(verifierId))
            throw new IllegalArgumentException("Permission required to verify this working hour.");
        currentWorkingHour.setVerified(verifyResult);
        workingHourMapper.updateByPrimaryKeySelective(currentWorkingHour);
    }

    public List<WorkingHour> GetToBeVerifiedWorkingHour(String projectId,String verifierId)
    {
        ResponseContent queryResponse=roleServiceClient.GetInferior(new HashMap<>()
        {{
            put("project_id",
                projectId);
            put("superior_id",
                verifierId);
        }});

        List<String> queryResult=objectMapper.convertValue(queryResponse.getResult(),
                                                           new TypeReference<List<String>>()
                                                           {
                                                           });
        if(queryResult==null||queryResult.isEmpty())
            return null;
        return workingHourMapper.selectByProjectIdAndUserIds(projectId,
                                                             queryResult);
    }

    public List<ActivityType> ListAllActivityType()
    {
        return activityTypeMapper.selectAll();
    }
}
