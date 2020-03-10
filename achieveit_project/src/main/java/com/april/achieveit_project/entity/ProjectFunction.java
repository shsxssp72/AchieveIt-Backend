package com.april.achieveit_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFunction {
    private Long functionId;

    private String referredProjectId;

    private Long superiorFunctionId;

    private String description;
}