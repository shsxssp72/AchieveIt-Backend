package com.april.achieveit_project.entity;

public class ProjectMiscellaneous {
    private Integer miscId;

    private String referredProjectId;

    private String keyField;

    private String valueField;

    public ProjectMiscellaneous(Integer miscId, String referredProjectId, String keyField, String valueField) {
        this.miscId = miscId;
        this.referredProjectId = referredProjectId;
        this.keyField = keyField;
        this.valueField = valueField;
    }

    public ProjectMiscellaneous() {
        super();
    }

    public Integer getMiscId() {
        return miscId;
    }

    public void setMiscId(Integer miscId) {
        this.miscId = miscId;
    }

    public String getReferredProjectId() {
        return referredProjectId;
    }

    public void setReferredProjectId(String referredProjectId) {
        this.referredProjectId = referredProjectId == null ? null : referredProjectId.trim();
    }

    public String getKeyField() {
        return keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField == null ? null : keyField.trim();
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField == null ? null : valueField.trim();
    }
}