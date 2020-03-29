package com.april.achieveit_project.service;

import com.april.achieveit_common.utility.RedisCacheUtility;
import com.april.achieveit_common.utility.SnowFlakeIdGenerator;
import com.april.achieveit_project.config.DeviceStateTransition;
import com.april.achieveit_project.entity.DeviceExamination;
import com.april.achieveit_project.entity.DeviceInfo;
import com.april.achieveit_project.entity.DeviceTenancy;
import com.april.achieveit_project.mapper.DeviceExaminationMapper;
import com.april.achieveit_project.mapper.DeviceInfoMapper;
import com.april.achieveit_project.mapper.DeviceTenancyMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProjectDeviceService extends RedisCacheUtility.AbstractRedisCacheService
{
    private static Logger logger=LoggerFactory.getLogger(ProjectDeviceService.class);

    static
    {
        for(Method method: ProjectDeviceService.class.getDeclaredMethods())
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
    DeviceInfoMapper deviceInfoMapper;
    @Autowired
    DeviceExaminationMapper deviceExaminationMapper;
    @Autowired
    DeviceTenancyMapper deviceTenancyMapper;
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

    public List<DeviceInfo> ListAllDevices(int pageSize,int currentPage)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<List<DeviceInfo>>(redisTemplate,
                                                                                      objectMapper,
                                                                                      reentrantLocks.get(currentMethodName),
                                                                                      cacheValidTime,
                                                                                      cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+pageSize+"_"+currentPage;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->
                                                {
                                                    PageHelper.startPage(currentPage,
                                                                         pageSize);
                                                    return deviceInfoMapper.selectAll();
                                                });
    }

    public DeviceInfo SelectDeviceInfoById(Long deviceId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<DeviceInfo>(redisTemplate,
                                                                                objectMapper,
                                                                                reentrantLocks.get(currentMethodName),
                                                                                cacheValidTime,
                                                                                cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+deviceId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->deviceInfoMapper.selectByPrimaryKey(deviceId));
    }

    public void UpdateDeviceStatus(Long deviceId,String status)
    {
        DeviceInfo deviceInfo=SelectDeviceInfoById(deviceId);
        String currentStatus=deviceInfo.getDeviceStatus();
        DeviceStateTransition transition=DeviceStateTransition.getInstance();
        if(!transition.isValidTransition(DeviceStateTransition.DeviceState.valueOf(currentStatus),
                                         DeviceStateTransition.DeviceState.valueOf(status)))
            throw new IllegalArgumentException("Invalid transition from: "+currentStatus+" to: "+status);
        var toUpdateDevice=new DeviceInfo();
        toUpdateDevice.setDeviceId(deviceId);
        toUpdateDevice.setDeviceStatus(status);
        deviceInfoMapper.updateByPrimaryKeySelective(toUpdateDevice);
    }

    public void NewTenancy(DeviceTenancy tenancy)
    {
        Long deviceId=tenancy.getReferredDeviceId();
        DeviceInfo deviceInfo=SelectDeviceInfoById(deviceId);
        if(!deviceInfo.getDeviceStatus()
                .equals(DeviceStateTransition.DeviceState.Available.name()))
            throw new IllegalArgumentException("Device not available");
        tenancy.setTenancyId(snowFlakeIdGenerator.getNextId());
        deviceTenancyMapper.insert(tenancy);

        var toUpdateDevice=new DeviceInfo();
        toUpdateDevice.setDeviceId(deviceId);
        toUpdateDevice.setDeviceStatus(DeviceStateTransition.DeviceState.LentOut.name());
        deviceInfoMapper.updateByPrimaryKeySelective(toUpdateDevice);
    }

    public void CheckDevice(DeviceExamination deviceExamination)
    {
        deviceExamination.setExaminationId(snowFlakeIdGenerator.getNextId());
        deviceExaminationMapper.insert(deviceExamination);
    }
}
