package com.april.achieveit_project.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Risk
{
    @JsonProperty(value="risk_id")
    private String riskId;
    @JsonProperty(value="referred_project_id")
    private String referredProjectId;
    @JsonProperty(value="risk_type")
    private String riskType;
    @JsonProperty(value="risk_description")
    private String riskDescription;
    @JsonProperty(value="risk_level")
    private String riskLevel;
    @JsonProperty(value="risk_impact")
    private String riskImpact;
    @JsonProperty(value="risk_countermeasure")
    private String riskCountermeasure;
    @JsonProperty(value="risk_status")
    private String riskStatus;
    @JsonProperty(value="risk_track_frequency")
    private String riskTrackFrequency;
}