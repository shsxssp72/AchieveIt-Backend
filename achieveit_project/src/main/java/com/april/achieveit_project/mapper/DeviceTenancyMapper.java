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

    int insert(DeviceTenancy record);

    int insertSelective(DeviceTenancy record);

    DeviceTenancy selectByPrimaryKey(@Param(value="tenancyId") Long tenancyId);

    int updateByPrimaryKeySelective(DeviceTenancy record);

    int updateByPrimaryKey(DeviceTenancy record);
}