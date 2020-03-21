package com.april.achieveit_project.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskRelatedPeople
{
    @JsonProperty(value="referred_risk_id")
    private String referredRiskId;
    @JsonProperty(value="referred_related_person_id")
    private String referredRelatedPersonId;
}