package com.april.achieveit_project.entity;

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

    public Risk(String riskId, String referredProjectId, String riskType, String riskDescription, String riskLevel, String riskImpact, String riskCountermeasure, String riskStatus, String referredRiskResponsiblePersonId, String riskTrackFrequency) {
        this.riskId = riskId;
        this.referredProjectId = referredProjectId;
        this.riskType = riskType;
        this.riskDescription = riskDescription;
        this.riskLevel = riskLevel;
        this.riskImpact = riskImpact;
        this.riskCountermeasure = riskCountermeasure;
        this.riskStatus = riskStatus;
        this.referredRiskResponsiblePersonId = referredRiskResponsiblePersonId;
        this.riskTrackFrequency = riskTrackFrequency;
    }

    public Risk() {
        super();
    }

    public String getRiskId() {
        return riskId;
    }

    public void setRiskId(String riskId) {
        this.riskId = riskId == null ? null : riskId.trim();
    }

    public String getReferredProjectId() {
        return referredProjectId;
    }

    public void setReferredProjectId(String referredProjectId) {
        this.referredProjectId = referredProjectId == null ? null : referredProjectId.trim();
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType == null ? null : riskType.trim();
    }

    public String getRiskDescription() {
        return riskDescription;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription == null ? null : riskDescription.trim();
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel == null ? null : riskLevel.trim();
    }

    public String getRiskImpact() {
        return riskImpact;
    }

    public void setRiskImpact(String riskImpact) {
        this.riskImpact = riskImpact == null ? null : riskImpact.trim();
    }

    public String getRiskCountermeasure() {
        return riskCountermeasure;
    }

    public void setRiskCountermeasure(String riskCountermeasure) {
        this.riskCountermeasure = riskCountermeasure == null ? null : riskCountermeasure.trim();
    }

    public String getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(String riskStatus) {
        this.riskStatus = riskStatus == null ? null : riskStatus.trim();
    }

    public String getReferredRiskResponsiblePersonId() {
        return referredRiskResponsiblePersonId;
    }

    public void setReferredRiskResponsiblePersonId(String referredRiskResponsiblePersonId) {
        this.referredRiskResponsiblePersonId = referredRiskResponsiblePersonId == null ? null : referredRiskResponsiblePersonId.trim();
    }

    public String getRiskTrackFrequency() {
        return riskTrackFrequency;
    }

    public void setRiskTrackFrequency(String riskTrackFrequency) {
        this.riskTrackFrequency = riskTrackFrequency == null ? null : riskTrackFrequency.trim();
    }
}