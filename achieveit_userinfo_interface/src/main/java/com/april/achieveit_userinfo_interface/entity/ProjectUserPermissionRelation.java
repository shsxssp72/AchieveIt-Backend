package com.april.achieveit_userinfo_interface.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUserPermissionRelation
{
    private String referredProjectId;

    private String referredUserId;

    private Long referredPermissionId;

    private Integer permitWeight;
}