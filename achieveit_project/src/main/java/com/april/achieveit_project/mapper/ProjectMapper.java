package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.Project;

public interface ProjectMapper {
    int deleteByPrimaryKey(String projectId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(String projectId);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}