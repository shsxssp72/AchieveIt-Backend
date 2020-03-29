package com.april.achieveit_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFunction
{
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @JsonProperty(value="function_id")
    private Long functionId;
    @JsonProperty(value="referred_project_id")
    private String referredProjectId;
    @JsonProperty(value="id_for_display")
    private String idForDisplay;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @JsonProperty(value="superior_function_id")
    private Long superiorFunctionId;
    @JsonProperty(value="description")
    private String description;
}