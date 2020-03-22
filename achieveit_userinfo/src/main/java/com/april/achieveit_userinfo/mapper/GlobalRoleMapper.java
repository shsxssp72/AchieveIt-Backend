package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.GlobalRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface GlobalRoleMapper
{
    int deleteByPrimaryKey(@Param(value="globalRoleId") Long globalRoleId);

    int insert(@Param(value="record") GlobalRole record);

    int insertSelective(@Param(value="record") GlobalRole record);

    GlobalRole selectByPrimaryKey(@Param(value="globalRoleId") Long globalRoleId);

    int updateByPrimaryKeySelective(@Param(value="record") GlobalRole record);

    int updateByPrimaryKey(@Param(value="record") GlobalRole record);
}