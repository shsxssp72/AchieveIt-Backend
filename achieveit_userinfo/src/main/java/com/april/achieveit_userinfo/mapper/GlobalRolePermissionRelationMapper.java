package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.GlobalRolePermissionRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface GlobalRolePermissionRelationMapper {
    int insert(@Param(value="record")GlobalRolePermissionRelation record);

    int insertSelective(@Param(value="record")GlobalRolePermissionRelation record);
}