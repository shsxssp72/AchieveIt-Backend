package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.ProjectMiscellaneous;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectMiscellaneousMapper
{
    int deleteByPrimaryKey(Integer miscId);

    int insert(ProjectMiscellaneous record);

    int insertSelective(ProjectMiscellaneous record);

    ProjectMiscellaneous selectByPrimaryKey(Integer miscId);

    List<ProjectMiscellaneous> selectByProjectId(String projectId);

    int updateByPrimaryKeySelective(ProjectMiscellaneous record);

    int updateByPrimaryKey(ProjectMiscellaneous record);
}