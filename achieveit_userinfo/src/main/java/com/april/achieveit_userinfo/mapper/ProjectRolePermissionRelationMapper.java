package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectRolePermissionRelation;

public interface ProjectRolePermissionRelationMapper {
    int insert(ProjectRolePermissionRelation record);

    int insertSelective(ProjectRolePermissionRelation record);
}