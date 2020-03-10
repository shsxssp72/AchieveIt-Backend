package com.april.achieveit_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingHour
{
    private Long workingHourId;

    private String referredUserId;

    private String referredProjectId;

    private Long referredActivityTypeId;

    private Long referredFunctionId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Boolean verified;
}