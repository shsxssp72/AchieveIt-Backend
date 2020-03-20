package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface ProjectMapper
{
    int deleteByPrimaryKey(String projectId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(String projectId);

    List<Project> selectByProjectNameAndStatus(String projectName,Set<String> validStatus);

    List<Project> selectByProjectIds(Set<String> projectIds);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}