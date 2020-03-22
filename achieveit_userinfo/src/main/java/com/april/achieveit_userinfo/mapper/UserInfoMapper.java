package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserInfoMapper
{
    int deleteByPrimaryKey(@Param(value="userId") String userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(@Param(value="userId") String userId);

    UserInfo selectByUsername(@Param(value="username") String username);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}