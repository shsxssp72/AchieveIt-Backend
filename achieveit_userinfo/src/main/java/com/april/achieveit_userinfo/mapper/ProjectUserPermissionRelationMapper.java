package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectUserPermissionRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectUserPermissionRelationMapper
{
    int insert(ProjectUserPermissionRelation record);

    int insertSelective(ProjectUserPermissionRelation record);

    List<ProjectUserPermissionRelation> selectByProjectIdAndUserId(String projectId,String userId);

    ProjectUserPermissionRelation selectByProjectIdAndUserIdAndPermissionId(String projectId,String userId,Long permissionId);

    int deleteByProjectIdAndUserIdAndPermissionId(String projectId,String userId,Long permissionId);

    int updateWeightByProjectIdAndUserIdAndPermissionId(int weight,String projectId,String userId,Long permissionId);
}