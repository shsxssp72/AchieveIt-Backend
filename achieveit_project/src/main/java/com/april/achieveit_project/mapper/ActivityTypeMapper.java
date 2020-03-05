package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.ActivityType;

public interface ActivityTypeMapper {
    int deleteByPrimaryKey(Integer activityTypeId);

    int insert(ActivityType record);

    int insertSelective(ActivityType record);

    ActivityType selectByPrimaryKey(Integer activityTypeId);

    int updateByPrimaryKeySelective(ActivityType record);

    int updateByPrimaryKey(ActivityType record);
}