package com.april.achieveit_userinfo_interface.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo
{
    private String userId;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long referredOuterUserId;

    private String userName;

    private String userPassword;

    private String userSalt;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long referredUserGlobalRoleId;
}