package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_project.entity.DeviceExamination;
import com.april.achieveit_project.entity.DeviceTenancy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/project/device")
public class ProjectDeviceController
{
    //TODO Placeholder

    private static Logger logger=LoggerFactory.getLogger(ProjectDeviceController.class);

    @GetMapping(path="/")
    public ResponseContent GetDevices(@RequestBody Map<String,String> params)
    {
        return null;
    }

    @GetMapping(path="/{device_id}")
    public ResponseContent GetDeviceInfo(@PathVariable(name="device_id") String deviceId)
    {
        return null;
    }

    @PostMapping(path="/{device_id}")
    public ResponseContent UpdateDeviceInfo(@PathVariable(name="device_id") String deviceId,@RequestBody Map<String,String> params)
    {
        return null;
    }

    @PostMapping(path="/tenancy")
    public ResponseContent NewTenancy(@RequestBody DeviceTenancy tenancy)
    {
        return null;
    }

    @PostMapping(path="/check")
    public ResponseContent CheckDevice(@RequestBody DeviceExamination examination)
    {
        return null;
    }
}
