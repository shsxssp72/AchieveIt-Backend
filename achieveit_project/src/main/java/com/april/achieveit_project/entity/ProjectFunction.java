package com.april.achieveit_project.entity;

public class ProjectFunction {
    private Integer functionId;

    private String referredProjectId;

    private Integer superiorFunctionId;

    private String description;

    public ProjectFunction(Integer functionId, String referredProjectId, Integer superiorFunctionId, String description) {
        this.functionId = functionId;
        this.referredProjectId = referredProjectId;
        this.superiorFunctionId = superiorFunctionId;
        this.description = description;
    }

    public ProjectFunction() {
        super();
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public String getReferredProjectId() {
        return referredProjectId;
    }

    public void setReferredProjectId(String referredProjectId) {
        this.referredProjectId = referredProjectId == null ? null : referredProjectId.trim();
    }

    public Integer getSuperiorFunctionId() {
        return superiorFunctionId;
    }

    public void setSuperiorFunctionId(Integer superiorFunctionId) {
        this.superiorFunctionId = superiorFunctionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}