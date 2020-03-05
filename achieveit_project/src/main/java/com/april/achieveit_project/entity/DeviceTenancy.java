package com.april.achieveit_project.entity;

import java.util.Date;

public class DeviceTenancy {
    private Integer tenancyId;

    private String referredProjectId;

    private Integer referredDeviceId;

    private Date tenancyTime;

    private Date scheduledReturnTime;

    private String referredDeviceManagerId;

    public DeviceTenancy(Integer tenancyId, String referredProjectId, Integer referredDeviceId, Date tenancyTime, Date scheduledReturnTime, String referredDeviceManagerId) {
        this.tenancyId = tenancyId;
        this.referredProjectId = referredProjectId;
        this.referredDeviceId = referredDeviceId;
        this.tenancyTime = tenancyTime;
        this.scheduledReturnTime = scheduledReturnTime;
        this.referredDeviceManagerId = referredDeviceManagerId;
    }

    public DeviceTenancy() {
        super();
    }

    public Integer getTenancyId() {
        return tenancyId;
    }

    public void setTenancyId(Integer tenancyId) {
        this.tenancyId = tenancyId;
    }

    public String getReferredProjectId() {
        return referredProjectId;
    }

    public void setReferredProjectId(String referredProjectId) {
        this.referredProjectId = referredProjectId == null ? null : referredProjectId.trim();
    }

    public Integer getReferredDeviceId() {
        return referredDeviceId;
    }

    public void setReferredDeviceId(Integer referredDeviceId) {
        this.referredDeviceId = referredDeviceId;
    }

    public Date getTenancyTime() {
        return tenancyTime;
    }

    public void setTenancyTime(Date tenancyTime) {
        this.tenancyTime = tenancyTime;
    }

    public Date getScheduledReturnTime() {
        return scheduledReturnTime;
    }

    public void setScheduledReturnTime(Date scheduledReturnTime) {
        this.scheduledReturnTime = scheduledReturnTime;
    }

    public String getReferredDeviceManagerId() {
        return referredDeviceManagerId;
    }

    public void setReferredDeviceManagerId(String referredDeviceManagerId) {
        this.referredDeviceManagerId = referredDeviceManagerId == null ? null : referredDeviceManagerId.trim();
    }
}