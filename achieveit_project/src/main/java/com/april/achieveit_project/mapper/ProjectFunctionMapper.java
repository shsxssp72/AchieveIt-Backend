package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.ProjectFunction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectFunctionMapper
{
    int deleteByPrimaryKey(@Param(value="functionId") Long functionId);

    int insert(@Param(value="record") ProjectFunction record);

    int insertSelective(@Param(value="record") ProjectFunction record);

    ProjectFunction selectByPrimaryKey(@Param(value="functionId") Long functionId);

    List<ProjectFunction> selectByProjectId(@Param(value="projectId") String projectId);

    int updateByPrimaryKeySelective(@Param(value="record") ProjectFunction record);

    int updateByPrimaryKey(@Param(value="record") ProjectFunction record);

    int deleteByProjectId(@Param(value="projectId") String projectId);
}