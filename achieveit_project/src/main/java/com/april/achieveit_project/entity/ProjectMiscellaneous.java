package com.april.achieveit_project.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMiscellaneous
{
    @JsonProperty(value="misc_id")
    private Long miscId;
    @JsonProperty(value="referred_project_id")
    private String referredProjectId;
    @JsonProperty(value="key_field")
    private String keyField;
    @JsonProperty(value="value_field")
    private String valueField;
}