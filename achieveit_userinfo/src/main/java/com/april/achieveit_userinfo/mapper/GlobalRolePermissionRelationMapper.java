package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.GlobalRolePermissionRelation;

public interface GlobalRolePermissionRelationMapper {
    int insert(GlobalRolePermissionRelation record);

    int insertSelective(GlobalRolePermissionRelation record);
}