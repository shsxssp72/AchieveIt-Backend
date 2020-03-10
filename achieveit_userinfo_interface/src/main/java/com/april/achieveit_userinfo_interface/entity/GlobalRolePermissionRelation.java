package com.april.achieveit_userinfo_interface.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalRolePermissionRelation
{
    private Integer referredGlobalRoleId;

    private Integer referredPermissionId;
}