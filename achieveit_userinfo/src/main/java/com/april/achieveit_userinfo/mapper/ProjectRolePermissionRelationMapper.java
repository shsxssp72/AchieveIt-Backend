package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectRolePermissionRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectRolePermissionRelationMapper
{
    int insert(ProjectRolePermissionRelation record);

    int insertSelective(ProjectRolePermissionRelation record);

    List<ProjectRolePermissionRelation> selectByProjectRoleId(@Param(value="projectRoleId") Long projectRoleId);
}