package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.WorkingHour;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WorkingHourMapper
{
    int deleteByPrimaryKey(@Param(value="workingHourId")Long workingHourId);

    int insert(@Param(value="record")WorkingHour record);

    int insertSelective(@Param(value="record")WorkingHour record);

    WorkingHour selectByPrimaryKey(@Param(value="workingHourId")Long workingHourId);

    List<WorkingHour> selectByProjectIdAndUserIds(@Param(value="projectId")String projectId,@Param(value="userId")List<String> userId);

    int updateByPrimaryKeySelective(@Param(value="record")WorkingHour record);

    int updateByPrimaryKey(@Param(value="record")WorkingHour record);
}