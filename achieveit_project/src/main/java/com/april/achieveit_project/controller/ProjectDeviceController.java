package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_common.utility.JsonVisibilityLevel;
import com.april.achieveit_project.entity.DeviceExamination;
import com.april.achieveit_project.entity.DeviceTenancy;
import com.april.achieveit_project.service.ProjectDeviceService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/device")
public class ProjectDeviceController
{
    private static Logger logger=LoggerFactory.getLogger(ProjectDeviceController.class);
    @Autowired
    private ProjectDeviceService projectDeviceService;

    @PostMapping(path="")
    @JsonView(value=JsonVisibilityLevel.BasicViewLevel.class)
    public ResponseContent GetDevices(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        int pageSize=Integer.parseInt(params.get("page_size"));
        int currentPage=Integer.parseInt(params.get("current_page"));

        result.setResult(new HashMap<>()
        {{
            put("device_id",
                projectDeviceService.ListAllDevices(pageSize,
                                                    currentPage));
        }});

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @GetMapping(path="/{device_id}")
    public ResponseContent GetDeviceInfo(@PathVariable(name="device_id") String deviceId)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        result.setResult(projectDeviceService.SelectDeviceInfoById(Long.parseLong(deviceId)));

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PutMapping(path="/{device_id}")
    public ResponseContent UpdateDeviceInfo(@PathVariable(name="device_id") String deviceId,@RequestBody Map<String,String> params)//TODO Check Role
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        String deviceStatus=params.get("device_status");

        projectDeviceService.UpdateDeviceStatus(Long.parseLong(deviceId),
                                                deviceStatus);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PutMapping(path="/tenancy")
    public ResponseContent NewTenancy(@RequestBody DeviceTenancy tenancy)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        projectDeviceService.NewTenancy(tenancy);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PutMapping(path="/check")
    public ResponseContent CheckDevice(@RequestBody DeviceExamination examination)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        projectDeviceService.CheckDevice(examination);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }
}
