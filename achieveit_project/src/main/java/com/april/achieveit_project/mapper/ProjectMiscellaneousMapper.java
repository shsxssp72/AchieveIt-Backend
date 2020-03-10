package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.ProjectMiscellaneous;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectMiscellaneousMapper
{
    int deleteByPrimaryKey(Long miscId);

    int insert(ProjectMiscellaneous record);

    int insertSelective(ProjectMiscellaneous record);

    ProjectMiscellaneous selectByPrimaryKey(Long miscId);

    List<ProjectMiscellaneous> selectByProjectId(String projectId);

    ProjectMiscellaneous selectByProjectIdAndKey(String projectId,String key);

    int updateByPrimaryKeySelective(ProjectMiscellaneous record);

    int updateByPrimaryKey(ProjectMiscellaneous record);
}