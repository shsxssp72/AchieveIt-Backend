package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.GlobalRolePermissionRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface GlobalRolePermissionRelationMapper
{
    int insert(GlobalRolePermissionRelation record);

    int insertSelective(GlobalRolePermissionRelation record);
}