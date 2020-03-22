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
    int deleteByPrimaryKey(@Param(value="activityTypeId")Long activityTypeId);

    int insert(@Param(value="record")ActivityType record);

    int insertSelective(@Param(value="record")ActivityType record);

    ActivityType selectByPrimaryKey(@Param(value="activityTypeId")Long activityTypeId);

    List<ActivityType> selectAll();

    int updateByPrimaryKeySelective(@Param(value="record")ActivityType record);

    int updateByPrimaryKey(@Param(value="record")ActivityType record);
}