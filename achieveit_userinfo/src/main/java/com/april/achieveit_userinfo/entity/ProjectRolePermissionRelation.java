package com.april.achieveit_userinfo.entity;

public class ProjectRolePermissionRelation {
    private Integer referredProjectRoleId;

    private Integer referredPermissionId;

    public ProjectRolePermissionRelation(Integer referredProjectRoleId, Integer referredPermissionId) {
        this.referredProjectRoleId = referredProjectRoleId;
        this.referredPermissionId = referredPermissionId;
    }

    public ProjectRolePermissionRelation() {
        super();
    }

    public Integer getReferredProjectRoleId() {
        return referredProjectRoleId;
    }

    public void setReferredProjectRoleId(Integer referredProjectRoleId) {
        this.referredProjectRoleId = referredProjectRoleId;
    }

    public Integer getReferredPermissionId() {
        return referredPermissionId;
    }

    public void setReferredPermissionId(Integer referredPermissionId) {
        this.referredPermissionId = referredPermissionId;
    }
}