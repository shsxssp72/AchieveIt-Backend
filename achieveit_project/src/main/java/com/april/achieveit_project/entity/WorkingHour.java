package com.april.achieveit_project.entity;

import com.april.achieveit_common.utility.JsonVisibilityLevel;
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
public class WorkingHour
{
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @JsonProperty(value="working_hour_id")
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private Long workingHourId;
    @JsonProperty(value="function_description_snapshot")
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private String functionDescriptionSnapshot;
    @JsonProperty(value="referred_user_id")
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    private String referredUserId;
    @JsonProperty(value="referred_project_id")
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    private String referredProjectId;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @JsonProperty(value="referred_activity_type_id")
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private Long referredActivityTypeId;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @JsonProperty(value="referred_function_id")
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private Long referredFunctionId;
    @JsonProperty(value="start_time")
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;
    @JsonProperty(value="end_time")
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;
    @JsonProperty(value="verified")
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private Boolean verified;
}