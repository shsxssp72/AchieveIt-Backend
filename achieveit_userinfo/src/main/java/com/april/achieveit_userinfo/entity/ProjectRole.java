package com.april.achieveit_userinfo.entity;

public class ProjectRole {
    private Integer projectRoleId;

    private String projectRoleName;

    public ProjectRole(Integer projectRoleId, String projectRoleName) {
        this.projectRoleId = projectRoleId;
        this.projectRoleName = projectRoleName;
    }

    public ProjectRole() {
        super();
    }

    public Integer getProjectRoleId() {
        return projectRoleId;
    }

    public void setProjectRoleId(Integer projectRoleId) {
        this.projectRoleId = projectRoleId;
    }

    public String getProjectRoleName() {
        return projectRoleName;
    }

    public void setProjectRoleName(String projectRoleName) {
        this.projectRoleName = projectRoleName == null ? null : projectRoleName.trim();
    }
}