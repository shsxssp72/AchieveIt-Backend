package com.april.achieveit_project.entity;

public class DeviceInfo {
    private Integer deviceId;

    private String deviceName;

    private String deviceStatus;

    public DeviceInfo(Integer deviceId, String deviceName, String deviceStatus) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceStatus = deviceStatus;
    }

    public DeviceInfo() {
        super();
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus == null ? null : deviceStatus.trim();
    }
}