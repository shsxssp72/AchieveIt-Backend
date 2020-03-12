package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.ProjectFunction;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectFunctionMapper
{
    int deleteByPrimaryKey(Long functionId);

    int insert(ProjectFunction record);

    int insertSelective(ProjectFunction record);

    ProjectFunction selectByPrimaryKey(Long functionId);

    List<ProjectFunction> selectByProjectId(String projectId);

    int updateByPrimaryKeySelective(ProjectFunction record);

    int updateByPrimaryKey(ProjectFunction record);

    int deleteByProjectId(String projectId);
}