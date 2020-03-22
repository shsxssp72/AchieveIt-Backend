package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProjectRoleMapper
{
    int deleteByPrimaryKey(@Param(value="projectRoleId") Long projectRoleId);

    int insert(@Param(value="record") ProjectRole record);

    int insertSelective(@Param(value="record") ProjectRole record);

    ProjectRole selectByPrimaryKey(@Param(value="projectRoleId") Long projectRoleId);

    int updateByPrimaryKeySelective(@Param(value="record") ProjectRole record);

    int updateByPrimaryKey(@Param(value="record") ProjectRole record);
}