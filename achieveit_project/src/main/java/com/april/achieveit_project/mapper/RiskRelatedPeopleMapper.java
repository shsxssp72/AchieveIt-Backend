package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.RiskRelatedPeople;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RiskRelatedPeopleMapper
{
    int insert(RiskRelatedPeople record);

    int insertSelective(RiskRelatedPeople record);

    List<RiskRelatedPeople> selectByRiskId(@Param(value="riskId") String riskId);

    int deleteByRiskId(@Param(value="riskId") String riskId);
}