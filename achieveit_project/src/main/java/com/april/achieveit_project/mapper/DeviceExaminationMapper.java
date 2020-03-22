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

    int insert(@Param(value="record") DeviceExamination record);

    int insertSelective(@Param(value="record") DeviceExamination record);

    DeviceExamination selectByPrimaryKey(@Param(value="examinationId") Long examinationId);

    int updateByPrimaryKeySelective(@Param(value="record") DeviceExamination record);

    int updateByPrimaryKey(@Param(value="record") DeviceExamination record);
}