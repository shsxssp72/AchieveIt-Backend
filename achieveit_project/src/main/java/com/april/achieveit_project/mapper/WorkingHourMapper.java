package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.WorkingHour;

public interface WorkingHourMapper {
    int deleteByPrimaryKey(Integer workingHourId);

    int insert(WorkingHour record);

    int insertSelective(WorkingHour record);

    WorkingHour selectByPrimaryKey(Integer workingHourId);

    int updateByPrimaryKeySelective(WorkingHour record);

    int updateByPrimaryKey(WorkingHour record);
}