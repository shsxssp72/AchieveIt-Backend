package com.april.achieveit_userinfo_interface.entity;

public class GlobalRolePermissionRelation {
    private Integer referredGlobalRoleId;

    private Integer referredPermissionId;

    public GlobalRolePermissionRelation(Integer referredGlobalRoleId, Integer referredPermissionId) {
        this.referredGlobalRoleId = referredGlobalRoleId;
        this.referredPermissionId = referredPermissionId;
    }

    public GlobalRolePermissionRelation() {
        super();
    }

    public Integer getReferredGlobalRoleId() {
        return referredGlobalRoleId;
    }

    public void setReferredGlobalRoleId(Integer referredGlobalRoleId) {
        this.referredGlobalRoleId = referredGlobalRoleId;
    }

    public Integer getReferredPermissionId() {
        return referredPermissionId;
    }

    public void setReferredPermissionId(Integer referredPermissionId) {
        this.referredPermissionId = referredPermissionId;
    }
}