package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo.entity.GlobalRole;

public interface GlobalRoleMapper {
    int deleteByPrimaryKey(Integer globalRoleId);

    int insert(GlobalRole record);

    int insertSelective(GlobalRole record);

    GlobalRole selectByPrimaryKey(Integer globalRoleId);

    int updateByPrimaryKeySelective(GlobalRole record);

    int updateByPrimaryKey(GlobalRole record);
}