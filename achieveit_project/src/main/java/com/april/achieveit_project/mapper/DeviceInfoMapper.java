package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.DeviceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DeviceInfoMapper
{
    int deleteByPrimaryKey(@Param(value="deviceId") Long deviceId);

    int insert(DeviceInfo record);

    int insertSelective(DeviceInfo record);

    DeviceInfo selectByPrimaryKey(@Param(value="deviceId") Long deviceId);

    List<DeviceInfo> selectAll();

    List<DeviceInfo> selectByProjectIdAndStatus(@Param(value="projectId") String projectId,@Param(value="deviceStatus") String deviceStatus);

    int updateByPrimaryKeySelective(DeviceInfo record);

    int updateByPrimaryKey(DeviceInfo record);
}