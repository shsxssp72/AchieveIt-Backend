package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectRole;

public interface ProjectRoleMapper {
    int deleteByPrimaryKey(Long projectRoleId);

    int insert(ProjectRole record);

    int insertSelective(ProjectRole record);

    ProjectRole selectByPrimaryKey(Long projectRoleId);

    int updateByPrimaryKeySelective(ProjectRole record);

    int updateByPrimaryKey(ProjectRole record);
}