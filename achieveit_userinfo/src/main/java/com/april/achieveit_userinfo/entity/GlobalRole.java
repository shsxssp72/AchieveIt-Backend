package com.april.achieveit_userinfo.entity;

public class GlobalRole {
    private Integer globalRoleId;

    private String globalRoleName;

    public GlobalRole(Integer globalRoleId, String globalRoleName) {
        this.globalRoleId = globalRoleId;
        this.globalRoleName = globalRoleName;
    }

    public GlobalRole() {
        super();
    }

    public Integer getGlobalRoleId() {
        return globalRoleId;
    }

    public void setGlobalRoleId(Integer globalRoleId) {
        this.globalRoleId = globalRoleId;
    }

    public String getGlobalRoleName() {
        return globalRoleName;
    }

    public void setGlobalRoleName(String globalRoleName) {
        this.globalRoleName = globalRoleName == null ? null : globalRoleName.trim();
    }
}