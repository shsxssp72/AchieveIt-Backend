package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.ProjectMiscellaneous;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProjectMiscellaneousMapper
{
    int deleteByPrimaryKey(@Param(value="miscId") Long miscId);

    int insert(@Param(value="record") ProjectMiscellaneous record);

    int insertSelective(@Param(value="record") ProjectMiscellaneous record);

    ProjectMiscellaneous selectByPrimaryKey(@Param(value="miscId") Long miscId);

    List<ProjectMiscellaneous> selectByProjectId(@Param(value="projectId") String projectId);

    ProjectMiscellaneous selectByProjectIdAndKey(@Param(value="projectId") String projectId,@Param(value="key") String key);

    int updateByPrimaryKeySelective(@Param(value="record") ProjectMiscellaneous record);

    int updateByPrimaryKey(@Param(value="record") ProjectMiscellaneous record);
}