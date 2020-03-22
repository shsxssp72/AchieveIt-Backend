package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PermissionMapper
{
    int deleteByPrimaryKey(@Param(value="permissionId") Long permissionId);

    int insert(@Param(value="record") Permission record);

    int insertSelective(@Param(value="record") Permission record);

    Permission selectByPrimaryKey(@Param(value="permissionId") Long permissionId);

    Permission selectByPermissionName(@Param(value="permissionName") String permissionName);

    int updateByPrimaryKeySelective(@Param(value="record") Permission record);

    int updateByPrimaryKey(@Param(value="record") Permission record);
}