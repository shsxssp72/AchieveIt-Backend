package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.DeviceExamination;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface DeviceExaminationMapper
{
    int deleteByPrimaryKey(Long examinationId);

    int insert(DeviceExamination record);

    int insertSelective(DeviceExamination record);

    DeviceExamination selectByPrimaryKey(Long examinationId);

    int updateByPrimaryKeySelective(DeviceExamination record);

    int updateByPrimaryKey(DeviceExamination record);
}