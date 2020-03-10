package com.april.achieveit_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMiscellaneous {
    private Long miscId;

    private String referredProjectId;

    private String keyField;

    private String valueField;
}