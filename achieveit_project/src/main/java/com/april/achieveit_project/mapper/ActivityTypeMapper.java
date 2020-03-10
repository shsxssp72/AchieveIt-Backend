package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.ActivityType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ActivityTypeMapper
{
    int deleteByPrimaryKey(Long activityTypeId);

    int insert(ActivityType record);

    int insertSelective(ActivityType record);

    ActivityType selectByPrimaryKey(Long activityTypeId);

    int updateByPrimaryKeySelective(ActivityType record);

    int updateByPrimaryKey(ActivityType record);
}