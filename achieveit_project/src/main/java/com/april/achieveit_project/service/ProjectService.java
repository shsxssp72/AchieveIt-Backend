package com.april.achieveit_project.service;

import com.april.achieveit_project.entity.Project;
import com.april.achieveit_project.mapper.ProjectMapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Project> SearchProjectByName(String projectName,int pageSize,int currentPage)
    {
        PageHelper.startPage(currentPage,pageSize);
        return projectMapper.selectByProjectName(projectName);
    }


}
