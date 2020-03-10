package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.DeviceTenancy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface DeviceTenancyMapper
{
    int deleteByPrimaryKey(Integer tenancyId);

    int insert(DeviceTenancy record);

    int insertSelective(DeviceTenancy record);

    DeviceTenancy selectByPrimaryKey(Integer tenancyId);

    int updateByPrimaryKeySelective(DeviceTenancy record);

    int updateByPrimaryKey(DeviceTenancy record);
}