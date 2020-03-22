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

    int insert(@Param(value="record") DeviceInfo record);

    int insertSelective(@Param(value="record") DeviceInfo record);

    DeviceInfo selectByPrimaryKey(@Param(value="deviceId") Long deviceId);

    List<DeviceInfo> selectAll();

    int updateByPrimaryKeySelective(@Param(value="record") DeviceInfo record);

    int updateByPrimaryKey(@Param(value="record") DeviceInfo record);
}