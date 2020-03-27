package com.april.achieveit_project.entity;

import com.april.achieveit_common.utility.JsonVisibilityLevel;
import com.april.achieveit_project.config.ProjectStateTransition;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value="project_id")
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private String projectId;
    @JsonProperty(value="status")
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private String status;
    @JsonProperty(value="project_name")
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private String projectName;
    @JsonProperty(value="referred_outer_customer_id")
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private String referredOuterCustomerId;
    @JsonProperty(value="scheduled_start_time")
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date scheduledStartTime;
    @JsonProperty(value="scheduled_end_time")
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date scheduledEndTime;
    @JsonProperty("referred_superior_id")
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private String referredSuperiorId;
    @JsonProperty(value="milestone")
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    private String mileStone;
    @JsonProperty(value="technology")
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private String technology;
    @JsonProperty("referred_business_field_id")
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private Long referredBusinessFieldId;
    @JsonProperty(value="main_function")
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    private String mainFunction;

    public void setStatus(ProjectStateTransition.ProjectState status)
    {
        this.status=status.name();
    }
}