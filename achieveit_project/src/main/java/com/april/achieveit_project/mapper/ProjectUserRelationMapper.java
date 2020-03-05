package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.ProjectUserRelation;

public interface ProjectUserRelationMapper {
    int insert(ProjectUserRelation record);

    int insertSelective(ProjectUserRelation record);
}