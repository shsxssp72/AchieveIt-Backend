package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectUserRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectUserRelationMapper
{
    int insert(ProjectUserRelation record);

    int insertSelective(ProjectUserRelation record);

    List<ProjectUserRelation> selectByProjectIdAndUserId(String projectId,String userId);
    List<ProjectUserRelation> selectBySuperiorId(String superiorId);
}