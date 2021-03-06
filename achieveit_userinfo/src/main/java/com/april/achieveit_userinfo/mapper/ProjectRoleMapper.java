package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectRoleMapper
{
    int deleteByPrimaryKey(@Param(value="projectRoleId") Long projectRoleId);

    int insert(ProjectRole record);

    int insertSelective(ProjectRole record);

    List<ProjectRole> selectAll();

    ProjectRole selectByPrimaryKey(@Param(value="projectRoleId") Long projectRoleId);

    int updateByPrimaryKeySelective(ProjectRole record);

    int updateByPrimaryKey(ProjectRole record);
}