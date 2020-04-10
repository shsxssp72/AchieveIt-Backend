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
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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

    @SneakyThrows
    public WorkingHour assembleWorkingHour(String projectId,Map<String,String> params)
    {
        WorkingHour workingHour=new WorkingHour();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        workingHour.setReferredProjectId(projectId);

        if(params.containsKey("working_hour_id"))
        {
            workingHour.setWorkingHourId(Long.parseLong(params.get("working_hour_id")));
        }
        if(params.containsKey("function_description_snapshot"))
        {
            workingHour.setFunctionDescriptionSnapshot(params.get("function_description_snapshot"));
        }
        if(params.containsKey("referred_user_id"))
        {
            workingHour.setReferredUserId(params.get("referred_user_id"));
        }
        if(params.containsKey("referred_activity_type_id"))
        {
            workingHour.setReferredActivityTypeId(Long.parseLong(params.get("referred_activity_type_id")));
        }
        if(params.containsKey("referred_function_id"))
        {
            ProjectFunction referredFunction=projectFunctionMapper.selectByProjectIdAndDisplayId(projectId,
                                                                                                 params.get("referred_function_id"));
            workingHour.setReferredFunctionId(referredFunction.getFunctionId());
        }
        if(params.containsKey("start_time"))
        {
            workingHour.setStartTime(dateFormat.parse(params.get("start_time")));
        }
        if(params.containsKey("end_time"))
        {
            workingHour.setEndTime(dateFormat.parse(params.get("end_time")));
        }
        if(params.containsKey("verified"))
        {
            workingHour.setVerified(Boolean.parseBoolean(params.get("verified")));
        }
        return workingHour;
    }

    public void NewWorkingHour(WorkingHour workingHour)
    {
        workingHour.setWorkingHourId(snowFlakeIdGenerator.getNextId());
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

    @SneakyThrows
    @SuppressWarnings(value="all")
    public void VerifyWorkingHour(Long workingHourId,Boolean verifyResult,String verifierId)
    {
        WorkingHour currentWorkingHour=selectWorkingHourByPrimaryKey(workingHourId);
        String creatorId=currentWorkingHour.getReferredUserId();
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
        Map<String,String> currentQueryResult=queryResult.get(0);

        List<Map<String,String>> projectRoleIdList=objectMapper.readValue(currentQueryResult.get("project_role_id_list"),
                                                                             new TypeReference<List<Map<String,String>>>()
                                                                             {
                                                                             });
        Set<String> superiors=projectRoleIdList.parallelStream()
                .map(i->i.get("superior_id"))
                .collect(Collectors.toSet());
        if(!superiors.contains(verifierId))
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
                                                             queryResult)
                .parallelStream()
                .filter(i->i.getVerified()==null||!i.getVerified())
                .collect(Collectors.toList());
    }

    public List<ActivityType> ListAllActivityType()
    {
        return activityTypeMapper.selectAll();
    }
}
