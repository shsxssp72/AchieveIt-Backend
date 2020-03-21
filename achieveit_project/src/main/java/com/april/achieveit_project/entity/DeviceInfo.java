package com.april.achieveit_project.entity;

import com.april.achieveit_common.utility.JsonVisibilityLevel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInfo
{
    @JsonProperty(value="device_id")
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private Long deviceId;
    @JsonProperty(value="device_name")
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private String deviceName;
    @JsonProperty(value="device_status")
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private String deviceStatus;
}