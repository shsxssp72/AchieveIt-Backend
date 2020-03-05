package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.RiskRelatedPeople;

public interface RiskRelatedPeopleMapper {
    int insert(RiskRelatedPeople record);

    int insertSelective(RiskRelatedPeople record);
}