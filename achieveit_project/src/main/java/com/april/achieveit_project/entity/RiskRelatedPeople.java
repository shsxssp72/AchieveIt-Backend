package com.april.achieveit_project.entity;

public class RiskRelatedPeople {
    private String referredRiskId;

    private String referredRelatedPersonId;

    public RiskRelatedPeople(String referredRiskId, String referredRelatedPersonId) {
        this.referredRiskId = referredRiskId;
        this.referredRelatedPersonId = referredRelatedPersonId;
    }

    public RiskRelatedPeople() {
        super();
    }

    public String getReferredRiskId() {
        return referredRiskId;
    }

    public void setReferredRiskId(String referredRiskId) {
        this.referredRiskId = referredRiskId == null ? null : referredRiskId.trim();
    }

    public String getReferredRelatedPersonId() {
        return referredRelatedPersonId;
    }

    public void setReferredRelatedPersonId(String referredRelatedPersonId) {
        this.referredRelatedPersonId = referredRelatedPersonId == null ? null : referredRelatedPersonId.trim();
    }
}