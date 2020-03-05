package com.april.achieveit_project.entity;

import java.util.Date;

public class Project {
    private String projectId;

    private String status;

    private String projectName;

    private String referredOuterCustomerId;

    private Date scheduledStartTime;

    private Date scheduledEndTime;

    private String mileStone;

    private String technology;

    private Integer referredBusinessFieldId;

    private String mainFunction;

    public Project(String projectId, String status, String projectName, String referredOuterCustomerId, Date scheduledStartTime, Date scheduledEndTime, String mileStone, String technology, Integer referredBusinessFieldId, String mainFunction) {
        this.projectId = projectId;
        this.status = status;
        this.projectName = projectName;
        this.referredOuterCustomerId = referredOuterCustomerId;
        this.scheduledStartTime = scheduledStartTime;
        this.scheduledEndTime = scheduledEndTime;
        this.mileStone = mileStone;
        this.technology = technology;
        this.referredBusinessFieldId = referredBusinessFieldId;
        this.mainFunction = mainFunction;
    }

    public Project() {
        super();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getReferredOuterCustomerId() {
        return referredOuterCustomerId;
    }

    public void setReferredOuterCustomerId(String referredOuterCustomerId) {
        this.referredOuterCustomerId = referredOuterCustomerId == null ? null : referredOuterCustomerId.trim();
    }

    public Date getScheduledStartTime() {
        return scheduledStartTime;
    }

    public void setScheduledStartTime(Date scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    public Date getScheduledEndTime() {
        return scheduledEndTime;
    }

    public void setScheduledEndTime(Date scheduledEndTime) {
        this.scheduledEndTime = scheduledEndTime;
    }

    public String getMileStone() {
        return mileStone;
    }

    public void setMileStone(String mileStone) {
        this.mileStone = mileStone == null ? null : mileStone.trim();
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology == null ? null : technology.trim();
    }

    public Integer getReferredBusinessFieldId() {
        return referredBusinessFieldId;
    }

    public void setReferredBusinessFieldId(Integer referredBusinessFieldId) {
        this.referredBusinessFieldId = referredBusinessFieldId;
    }

    public String getMainFunction() {
        return mainFunction;
    }

    public void setMainFunction(String mainFunction) {
        this.mainFunction = mainFunction == null ? null : mainFunction.trim();
    }
}