package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.DeviceTenancy;

public interface DeviceTenancyMapper {
    int deleteByPrimaryKey(Integer tenancyId);

    int insert(DeviceTenancy record);

    int insertSelective(DeviceTenancy record);

    DeviceTenancy selectByPrimaryKey(Integer tenancyId);

    int updateByPrimaryKeySelective(DeviceTenancy record);

    int updateByPrimaryKey(DeviceTenancy record);
}