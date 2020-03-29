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

    int insert(GlobalRole record);

    int insertSelective(GlobalRole record);

    GlobalRole selectByPrimaryKey(@Param(value="globalRoleId") Long globalRoleId);

    int updateByPrimaryKeySelective(GlobalRole record);

    int updateByPrimaryKey(GlobalRole record);
}