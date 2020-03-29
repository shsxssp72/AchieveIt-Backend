package com.april.achieveit_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTenancy
{
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @JsonProperty(value="tenancy_id")
    private Long tenancyId;
    @JsonProperty(value="referred_project_id")
    private String referredProjectId;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @JsonProperty(value="referred_device_id")
    private Long referredDeviceId;
    @JsonProperty(value="tenancy_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date tenancyTime;
    @JsonProperty(value="scheduled_return_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date scheduledReturnTime;
    @JsonProperty(value="referred_device_manager_id")
    private String referredDeviceManagerId;
}