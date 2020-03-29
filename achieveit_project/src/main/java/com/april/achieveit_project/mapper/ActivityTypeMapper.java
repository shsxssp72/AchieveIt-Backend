package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.ActivityType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ActivityTypeMapper
{
    int deleteByPrimaryKey(@Param(value="activityTypeId") Long activityTypeId);

    int insert(ActivityType record);

    int insertSelective(ActivityType record);

    ActivityType selectByPrimaryKey(@Param(value="activityTypeId") Long activityTypeId);

    List<ActivityType> selectAll();

    int updateByPrimaryKeySelective(ActivityType record);

    int updateByPrimaryKey(ActivityType record);
}