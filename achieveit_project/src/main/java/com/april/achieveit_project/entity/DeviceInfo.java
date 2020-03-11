package com.april.achieveit_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInfo {
    private Long deviceId;

    private String deviceName;

    private String deviceStatus;
}