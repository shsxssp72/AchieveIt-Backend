package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.Risk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RiskMapper
{
    int deleteByPrimaryKey(@Param(value="riskId") String riskId);

    int insert(Risk record);

    int insertSelective(Risk record);

    Risk selectByPrimaryKey(@Param(value="riskId") String riskId);

    List<Risk> selectByProjectId(@Param(value="projectId") String projectId);

    int updateByPrimaryKeySelective(Risk record);

    int updateByPrimaryKey(Risk record);
}