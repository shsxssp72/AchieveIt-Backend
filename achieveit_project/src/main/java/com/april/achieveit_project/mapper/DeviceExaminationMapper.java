package com.april.achieveit_project.mapper;

import com.april.achieveit_project.entity.DeviceExamination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface DeviceExaminationMapper
{
    int deleteByPrimaryKey(@Param(value="examinationId") Long examinationId);

    int insert(DeviceExamination record);

    int insertSelective(DeviceExamination record);

    DeviceExamination selectByPrimaryKey(@Param(value="examinationId") Long examinationId);

    int updateByPrimaryKeySelective(DeviceExamination record);

    int updateByPrimaryKey(DeviceExamination record);
}