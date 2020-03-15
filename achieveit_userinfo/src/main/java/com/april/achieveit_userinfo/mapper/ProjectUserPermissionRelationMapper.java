package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectUserPermissionRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProjectUserPermissionRelationMapper {
    int insert(ProjectUserPermissionRelation record);

    int insertSelective(ProjectUserPermissionRelation record);
}