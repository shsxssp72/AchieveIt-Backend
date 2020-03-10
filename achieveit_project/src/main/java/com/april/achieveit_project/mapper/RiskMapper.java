package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.Risk;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RiskMapper
{
    int deleteByPrimaryKey(String riskId);

    int insert(Risk record);

    int insertSelective(Risk record);

    Risk selectByPrimaryKey(String riskId);

    int updateByPrimaryKeySelective(Risk record);

    int updateByPrimaryKey(Risk record);
}