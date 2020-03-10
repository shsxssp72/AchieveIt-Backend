package com.april.achieveit_project.entity;

import com.april.achieveit_project.config.ProjectStateTransition;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project
{
    private String projectId;

    private String status;

    private String projectName;

    private String referredOuterCustomerId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date scheduledStartTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date scheduledEndTime;

    private String mileStone;

    private String technology;

    private Integer referredBusinessFieldId;

    private String mainFunction;

    public void setStatus(ProjectStateTransition.ProjectState status)
    {
        this.status=status.name();
    }
}