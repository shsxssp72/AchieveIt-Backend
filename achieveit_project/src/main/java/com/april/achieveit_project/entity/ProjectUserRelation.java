package com.april.achieveit_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUserRelation {
    private String referredProjectId;

    private String referredUserId;

    private String referredSuperiorId;

    private Integer referredProjectRoleId;
}