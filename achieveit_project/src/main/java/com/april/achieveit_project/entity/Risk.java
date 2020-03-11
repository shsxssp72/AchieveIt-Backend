package com.april.achieveit_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Risk {
    private String riskId;

    private String referredProjectId;

    private String riskType;

    private String riskDescription;

    private String riskLevel;

    private String riskImpact;

    private String riskCountermeasure;

    private String riskStatus;

    private String referredRiskResponsiblePersonId;

    private String riskTrackFrequency;
}