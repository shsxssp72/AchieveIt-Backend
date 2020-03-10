package com.april.achieveit_project.service;

import com.april.achieveit_project.entity.Project;
import com.april.achieveit_project.mapper.ProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService
{
    private static Logger logger=LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectMapper projectMapper;


    public void NewProject(Project project)
    {
        projectMapper.insert(project);
    }


}
