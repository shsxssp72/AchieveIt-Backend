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

    int insert(ProjectFunction record);

    int insertSelective(ProjectFunction record);

    ProjectFunction selectByPrimaryKey(@Param(value="functionId") Long functionId);

    ProjectFunction selectByProjectIdAndDisplayId(@Param(value="projectId") String projectId,@Param(value="displayId") String displayId);

    List<ProjectFunction> selectByProjectId(@Param(value="projectId") String projectId);

    int updateByPrimaryKeySelective(ProjectFunction record);

    int updateByPrimaryKey(ProjectFunction record);

    int deleteByProjectId(@Param(value="projectId") String projectId);
}