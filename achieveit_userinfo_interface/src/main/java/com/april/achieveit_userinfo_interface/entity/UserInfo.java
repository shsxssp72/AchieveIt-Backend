package com.april.achieveit_userinfo_interface.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo
{
    private String userId;

    private Integer referredOuterUserId;

    private String userName;

    private String userPassword;

    private String userSalt;

    private Integer referredUserGlobalRoleId;
}