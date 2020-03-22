package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.DeviceTenancy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface DeviceTenancyMapper
{
    int deleteByPrimaryKey(@Param(value="tenancyId") Long tenancyId);

    int insert(@Param(value="record") DeviceTenancy record);

    int insertSelective(@Param(value="record") DeviceTenancy record);

    DeviceTenancy selectByPrimaryKey(@Param(value="tenancyId") Long tenancyId);

    int updateByPrimaryKeySelective(@Param(value="record") DeviceTenancy record);

    int updateByPrimaryKey(@Param(value="record") DeviceTenancy record);
}