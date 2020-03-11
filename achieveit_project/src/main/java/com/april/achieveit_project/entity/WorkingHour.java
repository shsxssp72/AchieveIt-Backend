package com.april.achieveit_project.entity;

import com.april.achieveit_common.utility.JsonVisibilityLevel;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private Long workingHourId;
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    private String referredUserId;
    @JsonView(value=JsonVisibilityLevel.AuthenticatedViewLevel.class)
    private String referredProjectId;
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private Long referredActivityTypeId;
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    private Long referredFunctionId;
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonView(value=JsonVisibilityLevel.AdvancedViewLevel.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    private Boolean verified;
}