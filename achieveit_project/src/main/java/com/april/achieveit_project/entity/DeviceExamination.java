package com.april.achieveit_project.entity;

import java.util.Date;

public class DeviceExamination {
    private Integer examinationId;

    private Integer referredDeviceId;

    private Date examinationTime;

    private String referredTesterId;

    private String testResult;

    public DeviceExamination(Integer examinationId, Integer referredDeviceId, Date examinationTime, String referredTesterId, String testResult) {
        this.examinationId = examinationId;
        this.referredDeviceId = referredDeviceId;
        this.examinationTime = examinationTime;
        this.referredTesterId = referredTesterId;
        this.testResult = testResult;
    }

    public DeviceExamination() {
        super();
    }

    public Integer getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(Integer examinationId) {
        this.examinationId = examinationId;
    }

    public Integer getReferredDeviceId() {
        return referredDeviceId;
    }

    public void setReferredDeviceId(Integer referredDeviceId) {
        this.referredDeviceId = referredDeviceId;
    }

    public Date getExaminationTime() {
        return examinationTime;
    }

    public void setExaminationTime(Date examinationTime) {
        this.examinationTime = examinationTime;
    }

    public String getReferredTesterId() {
        return referredTesterId;
    }

    public void setReferredTesterId(String referredTesterId) {
        this.referredTesterId = referredTesterId == null ? null : referredTesterId.trim();
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult == null ? null : testResult.trim();
    }
}