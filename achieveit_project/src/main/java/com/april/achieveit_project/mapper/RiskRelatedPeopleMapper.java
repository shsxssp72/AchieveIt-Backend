package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.RiskRelatedPeople;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RiskRelatedPeopleMapper
{
    int insert(RiskRelatedPeople record);

    int insertSelective(RiskRelatedPeople record);
}