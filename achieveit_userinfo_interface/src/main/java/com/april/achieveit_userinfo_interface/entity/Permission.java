package com.april.achieveit_userinfo_interface.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission
{
    private Long permissionId;

    private String permissionName;
}