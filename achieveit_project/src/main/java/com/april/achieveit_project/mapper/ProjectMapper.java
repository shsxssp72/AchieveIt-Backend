package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface ProjectMapper
{
    int deleteByPrimaryKey(@Param(value="projectId") String projectId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(@Param(value="projectId") String projectId);

    List<Project> selectByProjectNameAndStatus(@Param(value="projectName") String projectName,@Param("validStatus") Set<String> validStatus);

    List<Project> selectByProjectIds(@Param(value="projectIds") Set<String> projectIds);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}