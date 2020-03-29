package com.april.achieveit_userinfo_interface.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalRolePermissionRelation
{
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long referredGlobalRoleId;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long referredPermissionId;
}