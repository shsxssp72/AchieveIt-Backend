package com.april.achieveit_project.entity;

import com.april.achieveit_common.utility.JsonVisibilityLevel;
import com.april.achieveit_project.config.ProjectStateTransition;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project
{
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private String projectId;
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private String status;
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private String projectName;
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private String referredOuterCustomerId;
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date scheduledStartTime;
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date scheduledEndTime;
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    private String mileStone;
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private String technology;
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private Long referredBusinessFieldId;
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    private String mainFunction;

    public void setStatus(ProjectStateTransition.ProjectState status)
    {
        this.status=status.name();
    }
}