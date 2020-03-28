package com.april.achieveit_project.service;

import com.april.achieveit_project.entity.DeviceExamination;
import com.april.achieveit_project.entity.DeviceInfo;
import com.april.achieveit_project.entity.DeviceTenancy;
import com.april.achieveit_project.mapper.DeviceExaminationMapper;
import com.april.achieveit_project.mapper.DeviceInfoMapper;
import com.april.achieveit_project.mapper.DeviceTenancyMapper;
import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ProjectDeviceServiceTest
{

    @Autowired
    private ProjectDeviceService projectDeviceService;
    @Autowired
    private DeviceInfoMapper deviceInfoMapper;
    @Autowired
    private DeviceExaminationMapper deviceExaminationMapper;
    @Autowired
    private DeviceTenancyMapper deviceTenancyMapper;
    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");


    @BeforeAll
    public void setUp()
    {
        DeviceInfo deviceInfo=new DeviceInfo(290102211596255233L,
                                             "部署服务器",
                                             "LentOut");
        DeviceInfo deviceInfo2=new DeviceInfo(290102211596255123L,
                                              "部署服务器2",
                                              "Available");
        deviceInfoMapper.insert(deviceInfo);
        deviceInfoMapper.insert(deviceInfo2);
        System.out.println("setUp");

    }

    @AfterAll
    public void tearDown()
    {
        deviceInfoMapper.deleteByPrimaryKey(290102211596255233L);
        deviceInfoMapper.deleteByPrimaryKey(290102211596255123L);
        System.out.println("tearDown");
    }

    @Test
    void listAllDevices()
    {
        List<DeviceInfo> list=projectDeviceService.ListAllDevices(5,
                                                                  1);
        MatcherAssert.assertThat(list.get(0)
                                         .getDeviceName(),
                                 is("部署服务器2"));
        MatcherAssert.assertThat(list.get(1)
                                         .getDeviceName(),
                                 is("部署服务器"));
    }

    @Test
    void selectDeviceInfoById()
    {
        MatcherAssert.assertThat(projectDeviceService.SelectDeviceInfoById(290102211596255233L)
                                         .getDeviceName(),
                                 is("部署服务器"));
    }

    @Transactional
    @Test
    void updateDeviceStatus()
    {
        projectDeviceService.UpdateDeviceStatus(290102211596255233L,
                                                "ToBeChecked");
        MatcherAssert.assertThat(deviceInfoMapper.selectByPrimaryKey(290102211596255233L)
                                         .getDeviceStatus(),
                                 is("ToBeChecked"));

    }

    @SneakyThrows
    @Transactional
    @Test
    void newTenancy()
    {
        DeviceTenancy deviceTenancy=new DeviceTenancy(1123L,
                                                      "2020-4577-D-01",
                                                      290102211596255123L,
                                                      dateFormat.parse("2020-01-01"),
                                                      dateFormat.parse("2020-01-02"),
                                                      "123456");
        projectDeviceService.NewTenancy(deviceTenancy);
        MatcherAssert.assertThat(projectDeviceService.SelectDeviceInfoById(290102211596255123L)
                                         .getDeviceStatus(),
                                 is("LentOut"));
    }

    @SneakyThrows
    @Transactional
    @Test
    void checkDevice()
    {
        DeviceExamination deviceExamination=new DeviceExamination(1L,
                                                                  290102211596255123L,
                                                                  dateFormat.parse("2020-01-01"),
                                                                  "SYKJ-20200201-0000",
                                                                  "good");
        projectDeviceService.CheckDevice(deviceExamination);
        System.out.println("checkDeviceSuccess");
    }
}