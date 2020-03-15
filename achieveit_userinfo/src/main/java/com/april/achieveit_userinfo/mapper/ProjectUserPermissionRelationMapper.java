package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectUserPermissionRelation;
import com.april.achieveit_userinfo_interface.entity.ProjectUserRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectUserPermissionRelationMapper {
    int insert(ProjectUserPermissionRelation record);

    int insertSelective(ProjectUserPermissionRelation record);

    List<ProjectUserPermissionRelation> selectByProjectIdAndUserId(String projectId,String userId);
}