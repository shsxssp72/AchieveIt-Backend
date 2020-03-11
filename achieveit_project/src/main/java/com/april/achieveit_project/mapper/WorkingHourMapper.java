package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.WorkingHour;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WorkingHourMapper
{
    int deleteByPrimaryKey(Long workingHourId);

    int insert(WorkingHour record);

    int insertSelective(WorkingHour record);

    WorkingHour selectByPrimaryKey(Long workingHourId);

    List<WorkingHour> selectByProjectIdAndUserIds(String projectId,List<String> userId);

    int updateByPrimaryKeySelective(WorkingHour record);

    int updateByPrimaryKey(WorkingHour record);
}