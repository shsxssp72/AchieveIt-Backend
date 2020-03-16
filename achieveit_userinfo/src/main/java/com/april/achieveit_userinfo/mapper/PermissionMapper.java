package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PermissionMapper
{
    int deleteByPrimaryKey(Long permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long permissionId);

    Permission selectByPermissionName(String permissionName);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}