package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProjectRoleMapper {
    int deleteByPrimaryKey(Long projectRoleId);

    int insert(ProjectRole record);

    int insertSelective(ProjectRole record);

    ProjectRole selectByPrimaryKey(Long projectRoleId);

    int updateByPrimaryKeySelective(ProjectRole record);

    int updateByPrimaryKey(ProjectRole record);
}