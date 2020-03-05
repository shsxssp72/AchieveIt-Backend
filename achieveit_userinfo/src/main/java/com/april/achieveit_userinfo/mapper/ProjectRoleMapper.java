package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo.entity.ProjectRole;

public interface ProjectRoleMapper {
    int deleteByPrimaryKey(Integer projectRoleId);

    int insert(ProjectRole record);

    int insertSelective(ProjectRole record);

    ProjectRole selectByPrimaryKey(Integer projectRoleId);

    int updateByPrimaryKeySelective(ProjectRole record);

    int updateByPrimaryKey(ProjectRole record);
}