package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.ProjectFunction;

public interface ProjectFunctionMapper {
    int deleteByPrimaryKey(Integer functionId);

    int insert(ProjectFunction record);

    int insertSelective(ProjectFunction record);

    ProjectFunction selectByPrimaryKey(Integer functionId);

    int updateByPrimaryKeySelective(ProjectFunction record);

    int updateByPrimaryKey(ProjectFunction record);
}