package com.april.achieveit_project.service;

import com.april.achieveit_project.entity.DeviceExamination;
import com.april.achieveit_project.entity.DeviceInfo;
import com.april.achieveit_project.entity.DeviceTenancy;
import com.april.achieveit_project.mapper.DeviceExaminationMapper;
import com.april.achieveit_project.mapper.DeviceInfoMapper;
import com.april.achieveit_project.mapper.DeviceTenancyMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectDeviceServiceTest {

    @Autowired
    private ProjectDeviceService projectDeviceService;
    @Autowired
    DeviceInfoMapper deviceInfoMapper;
    @Autowired
    DeviceExaminationMapper deviceExaminationMapper;
    @Autowired
    DeviceTenancyMapper deviceTenancyMapper;

    @Test
    void listAllDevices() {
        List<DeviceInfo> list = projectDeviceService.ListAllDevices(5,1);
        Assert.assertThat(list.get(0).getDeviceName(),is("手机"));
    }

    @Test
    void selectDeviceInfoById() {
 Assert.assertThat(projectDeviceService.SelectDeviceInfoById(1L).getDeviceName(),is("手机"));
    }

    @Transactional
    @Test
    void updateDeviceStatus() {
        projectDeviceService.UpdateDeviceStatus(1L,"LentOut");
        Assert.assertThat(deviceInfoMapper.selectByPrimaryKey(1L).getDeviceStatus(),is("LentOut"));

    }
    @Transactional
    @Test
    void newTenancy() {
        DeviceTenancy deviceTenancy = new DeviceTenancy(1123L,"2020-4577-D-01",1L,new Date(2020-01-01),new Date(2020-01-02),"123456");
        projectDeviceService.NewTenancy(deviceTenancy);
        Assert.assertThat(projectDeviceService.SelectDeviceInfoById(1L).getDeviceStatus(),is("LentOut"));
    }
    @Transactional
    @Test
    void checkDevice() {
        DeviceExamination deviceExamination = new DeviceExamination(1L,1L,new Date(2020-01-01),"SYKJ-20200201-0000","good");
        projectDeviceService.CheckDevice(deviceExamination); }
}