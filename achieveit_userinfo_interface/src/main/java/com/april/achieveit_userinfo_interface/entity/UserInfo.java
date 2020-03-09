package com.april.achieveit_userinfo_interface.entity;

public class UserInfo {
    private String userId;

    private Integer referredOuterUserId;

    private String userName;

    private String userPassword;

    private String userSalt;

    private Integer referredUserGlobalRoleId;

    public UserInfo(String userId, Integer referredOuterUserId, String userName, String userPassword, String userSalt, Integer referredUserGlobalRoleId) {
        this.userId = userId;
        this.referredOuterUserId = referredOuterUserId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userSalt = userSalt;
        this.referredUserGlobalRoleId = referredUserGlobalRoleId;
    }

    public UserInfo() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getReferredOuterUserId() {
        return referredOuterUserId;
    }

    public void setReferredOuterUserId(Integer referredOuterUserId) {
        this.referredOuterUserId = referredOuterUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt == null ? null : userSalt.trim();
    }

    public Integer getReferredUserGlobalRoleId() {
        return referredUserGlobalRoleId;
    }

    public void setReferredUserGlobalRoleId(Integer referredUserGlobalRoleId) {
        this.referredUserGlobalRoleId = referredUserGlobalRoleId;
    }
}