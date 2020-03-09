package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.Permission;

public interface PermissionMapper
{
    int deleteByPrimaryKey(Integer permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}