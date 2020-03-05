package com.april.achieveit_project.entity;

public class ProjectUserRelation {
    private String referredProjectId;

    private String referredUserId;

    private String referredSuperiorId;

    private Integer referredProjectRoleId;

    public ProjectUserRelation(String referredProjectId, String referredUserId, String referredSuperiorId, Integer referredProjectRoleId) {
        this.referredProjectId = referredProjectId;
        this.referredUserId = referredUserId;
        this.referredSuperiorId = referredSuperiorId;
        this.referredProjectRoleId = referredProjectRoleId;
    }

    public ProjectUserRelation() {
        super();
    }

    public String getReferredProjectId() {
        return referredProjectId;
    }

    public void setReferredProjectId(String referredProjectId) {
        this.referredProjectId = referredProjectId == null ? null : referredProjectId.trim();
    }

    public String getReferredUserId() {
        return referredUserId;
    }

    public void setReferredUserId(String referredUserId) {
        this.referredUserId = referredUserId == null ? null : referredUserId.trim();
    }

    public String getReferredSuperiorId() {
        return referredSuperiorId;
    }

    public void setReferredSuperiorId(String referredSuperiorId) {
        this.referredSuperiorId = referredSuperiorId == null ? null : referredSuperiorId.trim();
    }

    public Integer getReferredProjectRoleId() {
        return referredProjectRoleId;
    }

    public void setReferredProjectRoleId(Integer referredProjectRoleId) {
        this.referredProjectRoleId = referredProjectRoleId;
    }
}