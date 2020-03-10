package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.ProjectUserRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProjectUserRelationMapper {
    int insert(ProjectUserRelation record);

    int insertSelective(ProjectUserRelation record);
}