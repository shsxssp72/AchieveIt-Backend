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
public class DeviceExamination
{
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @JsonProperty(value="examination_id")
    private Long examinationId;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @JsonProperty(value="referred_device_id")
    private Long referredDeviceId;
    @JsonProperty(value="examination_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date examinationTime;
    @JsonProperty("referred_tester_id")
    private String referredTesterId;
    @JsonProperty(value="test_result")
    private String testResult;
}