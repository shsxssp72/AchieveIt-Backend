package com.april.achieveit_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceExamination
{
    private Long examinationId;

    private Long referredDeviceId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date examinationTime;

    private String referredTesterId;

    private String testResult;
}