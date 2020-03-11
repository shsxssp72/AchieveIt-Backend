package com.april.achieveit_project.service;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.utility.SnowFlakeIdGenerator;
import com.april.achieveit_project.entity.ActivityType;
import com.april.achieveit_project.entity.WorkingHour;
import com.april.achieveit_project.mapper.ActivityTypeMapper;
import com.april.achieveit_project.mapper.WorkingHourMapper;
import com.april.achieveit_userinfo_interface.api.RoleServiceApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectWorkingHourService
{
    private static Logger logger=LoggerFactory.getLogger(ProjectWorkingHourService.class);

    @Autowired
    private WorkingHourMapper workingHourMapper;
    @Autowired
    private ActivityTypeMapper activityTypeMapper;
    @Autowired
    private RoleServiceApi roleServiceApi;
    @Autowired
    ObjectMapper objectMapper;

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
        workingHourMapper.insert(workingHour);
    }

    public List<WorkingHour> SelectByProjectId(String projectId,String userId)
    {
        return workingHourMapper.selectByProjectIdAndUserIds(projectId,
                                                             List.of(userId));
    }

    public WorkingHour SelectByWorkingHourId(Long workingHourId,String userId)
    {
        WorkingHour queryResult=workingHourMapper.selectByPrimaryKey(workingHourId);
        if(!queryResult.getReferredUserId()
                .equals(userId))
            throw new IllegalArgumentException("User can only view his own working hour");
        return queryResult;
    }

    public void UpdateWorkingHour(WorkingHour workingHour,String userId)
    {
        Long workingHourId=workingHour.getWorkingHourId();
        WorkingHour currentWorkingHour=workingHourMapper.selectByPrimaryKey(workingHourId);
        if(!currentWorkingHour.getReferredUserId()
                .equals(userId))
            throw new IllegalArgumentException("User can only update his own working hour");
        workingHour.setVerified(false);
        workingHourMapper.updateByPrimaryKeySelective(workingHour);
    }

    public void VerifyWorkingHour(Long workingHourId,Boolean verifyResult,String verifierId)
    {
        WorkingHour currentWorkingHour=workingHourMapper.selectByPrimaryKey(workingHourId);
        String creatorId=currentWorkingHour.getReferredProjectId();
        String projectId=currentWorkingHour.getReferredProjectId();
        //TODO role controller api may change, confirm in the end
        ResponseContent queryResponse=roleServiceApi.GetUserProjectRole(new HashMap<>()
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
        ResponseContent queryResponse=roleServiceApi.GetInferior(new HashMap<>()
        {{
            put("superior_id",
                verifierId);
        }});

        List<String> queryResult=objectMapper.convertValue(queryResponse.getResult(),
                                                           new TypeReference<List<String>>()
                                                           {
                                                           });
        return workingHourMapper.selectByProjectIdAndUserIds(projectId,
                                                             queryResult);
    }

    public List<ActivityType> ListAllActivityType()
    {
        return activityTypeMapper.selectAll();
    }
}
