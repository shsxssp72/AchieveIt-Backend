package com.april.achieveit_userinfo.mapper;

import com.april.achieveit_userinfo_interface.entity.ProjectUserRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectUserRelationMapper
{
    int insert(ProjectUserRelation record);

    int insertSelective(ProjectUserRelation record);

    List<ProjectUserRelation> selectByProjectIdAndUserId(@Param(value="projectId") String projectId,@Param(value="userId") String userId);

    List<ProjectUserRelation> selectBySuperiorId(@Param(value="projectId") String projectId,@Param(value="superiorId") String superiorId);

    int deleteByProjectIdAndUserId(@Param(value="projectId") String projectId,@Param(value="userId") String userId);
}