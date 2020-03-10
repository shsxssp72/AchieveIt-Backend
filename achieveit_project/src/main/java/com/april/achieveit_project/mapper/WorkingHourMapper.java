package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.WorkingHour;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface WorkingHourMapper
{
    int deleteByPrimaryKey(Long workingHourId);

    int insert(WorkingHour record);

    int insertSelective(WorkingHour record);

    WorkingHour selectByPrimaryKey(Long workingHourId);

    int updateByPrimaryKeySelective(WorkingHour record);

    int updateByPrimaryKey(WorkingHour record);
}