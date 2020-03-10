package com.april.achieveit_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFunction {
    private Integer functionId;

    private String referredProjectId;

    private Integer superiorFunctionId;

    private String description;
}