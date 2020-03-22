package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectUserPermissionRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectUserPermissionRelationMapper
{
    int insert(@Param(value="record") ProjectUserPermissionRelation record);

    int insertSelective(@Param(value="record") ProjectUserPermissionRelation record);

    List<ProjectUserPermissionRelation> selectByProjectIdAndUserId(@Param(value="projectId") String projectId,@Param(value="userId") String userId);

    ProjectUserPermissionRelation selectByProjectIdAndUserIdAndPermissionId(@Param(value="projectId") String projectId,@Param(value="userId") String userId,@Param(value="permissionId") Long permissionId);

    int deleteByProjectIdAndUserIdAndPermissionId(@Param(value="projectId") String projectId,@Param(value="userId") String userId,@Param(value="permissionId") Long permissionId);

    int updateWeightByProjectIdAndUserIdAndPermissionId(@Param(value="weight") int weight,@Param(value="projectId") String projectId,@Param(value="userId") String userId,@Param(value="permissionId") Long permissionId);
}