package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.GlobalRole;

public interface GlobalRoleMapper
{
    int deleteByPrimaryKey(Long globalRoleId);

    int insert(GlobalRole record);

    int insertSelective(GlobalRole record);

    GlobalRole selectByPrimaryKey(Long globalRoleId);

    int updateByPrimaryKeySelective(GlobalRole record);

    int updateByPrimaryKey(GlobalRole record);
}