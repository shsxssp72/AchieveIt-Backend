package com.april.achieveit_project.entity;

public class ActivityType {
    private Integer activityTypeId;

    private String level1Description;

    private String level2Description;

    public ActivityType(Integer activityTypeId, String level1Description, String level2Description) {
        this.activityTypeId = activityTypeId;
        this.level1Description = level1Description;
        this.level2Description = level2Description;
    }

    public ActivityType() {
        super();
    }

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getLevel1Description() {
        return level1Description;
    }

    public void setLevel1Description(String level1Description) {
        this.level1Description = level1Description == null ? null : level1Description.trim();
    }

    public String getLevel2Description() {
        return level2Description;
    }

    public void setLevel2Description(String level2Description) {
        this.level2Description = level2Description == null ? null : level2Description.trim();
    }
}