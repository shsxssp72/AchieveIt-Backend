package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectMapper
{
    int deleteByPrimaryKey(String projectId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(String projectId);

    List<Project> selectByProjectName(String projectName);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}