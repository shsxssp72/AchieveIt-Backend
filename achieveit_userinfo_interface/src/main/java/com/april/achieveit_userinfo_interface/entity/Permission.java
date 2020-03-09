package com.april.achieveit_userinfo_interface.entity;

public class Permission {
    private Integer permissionId;

    private String permissionName;

    public Permission(Integer permissionId, String permissionName) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
    }

    public Permission() {
        super();
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }
}