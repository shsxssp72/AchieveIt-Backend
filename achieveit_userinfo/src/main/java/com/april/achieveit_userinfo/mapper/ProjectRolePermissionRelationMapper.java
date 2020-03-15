package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectRolePermissionRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProjectRolePermissionRelationMapper {
    int insert(ProjectRolePermissionRelation record);

    int insertSelective(ProjectRolePermissionRelation record);
}