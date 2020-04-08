package com.april.achieveit_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityType
{
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("activity_type_id")
    private Long activityTypeId;
    @JsonProperty(value="level_1_description")
    private String level1Description;
    @JsonProperty("level_2_description")
    private String level2Description;
}