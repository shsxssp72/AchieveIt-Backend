package com.april.achieveit_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityType {
    private Long activityTypeId;

    private String level1Description;

    private String level2Description;
}