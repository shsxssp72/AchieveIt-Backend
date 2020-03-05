package com.april.achieveit_project.entity;

import java.util.Date;

public class WorkingHour {
    private Integer workingHourId;

    private String referredUserId;

    private String referredProjectId;

    private Integer referredActivityTypeId;

    private Integer referredFunctionId;

    private Date startTime;

    private Date endTime;

    private Boolean verified;

    public WorkingHour(Integer workingHourId, String referredUserId, String referredProjectId, Integer referredActivityTypeId, Integer referredFunctionId, Date startTime, Date endTime, Boolean verified) {
        this.workingHourId = workingHourId;
        this.referredUserId = referredUserId;
        this.referredProjectId = referredProjectId;
        this.referredActivityTypeId = referredActivityTypeId;
        this.referredFunctionId = referredFunctionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.verified = verified;
    }

    public WorkingHour() {
        super();
    }

    public Integer getWorkingHourId() {
        return workingHourId;
    }

    public void setWorkingHourId(Integer workingHourId) {
        this.workingHourId = workingHourId;
    }

    public String getReferredUserId() {
        return referredUserId;
    }

    public void setReferredUserId(String referredUserId) {
        this.referredUserId = referredUserId == null ? null : referredUserId.trim();
    }

    public String getReferredProjectId() {
        return referredProjectId;
    }

    public void setReferredProjectId(String referredProjectId) {
        this.referredProjectId = referredProjectId == null ? null : referredProjectId.trim();
    }

    public Integer getReferredActivityTypeId() {
        return referredActivityTypeId;
    }

    public void setReferredActivityTypeId(Integer referredActivityTypeId) {
        this.referredActivityTypeId = referredActivityTypeId;
    }

    public Integer getReferredFunctionId() {
        return referredFunctionId;
    }

    public void setReferredFunctionId(Integer referredFunctionId) {
        this.referredFunctionId = referredFunctionId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}