package com.april.achieveit_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTenancy
{
    private Integer tenancyId;

    private String referredProjectId;

    private Integer referredDeviceId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date tenancyTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date scheduledReturnTime;

    private String referredDeviceManagerId;
}