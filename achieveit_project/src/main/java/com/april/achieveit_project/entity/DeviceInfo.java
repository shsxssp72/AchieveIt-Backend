package com.april.achieveit_project.entity;

import com.april.achieveit_common.utility.JsonVisibilityLevel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInfo {
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private Long deviceId;
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private String deviceName;
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private String deviceStatus;
}