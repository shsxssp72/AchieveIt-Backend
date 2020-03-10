package com.april.achieveit_project.service;

import com.april.achieveit_common.utility.SnowFlakeIdGenerator;
import com.april.achieveit_project.config.ProjectStateTransition;
import com.april.achieveit_project.entity.Project;
import com.april.achieveit_project.entity.ProjectMiscellaneous;
import com.april.achieveit_project.mapper.ProjectMapper;
import com.april.achieveit_project.mapper.ProjectMiscellaneousMapper;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService
{
    private static Logger logger=LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectMiscellaneousMapper projectMiscellaneousMapper;

    @Value("${snowflake.datacenter-id}")
    private Long datacenterId;
    @Value("${snowflake.machine-id}")
    private Long machineId;
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    @PostConstruct
    private void init()
    {
        snowFlakeIdGenerator=new SnowFlakeIdGenerator(datacenterId,
                                                      machineId);
    }


    public void NewProject(Project project)
    {
        projectMapper.insert(project);
    }

    public List<Project> SearchProjectByName(String projectName,int pageSize,int currentPage)
    {
        PageHelper.startPage(currentPage,
                             pageSize);
        return projectMapper.selectByProjectName(projectName);
    }

    public List<Project> ListRelativeProject(Set<String> projectIds,int pageSize,int currentPage)
    {
        PageHelper.startPage(currentPage,
                             pageSize);
        return projectMapper.selectByProjectIds(projectIds);
    }

    public Project SelectByProjectId(String projectId)
    {
        var queryResult=projectMapper.selectByPrimaryKey(projectId);
        if(queryResult==null)
            throw new IllegalArgumentException("No matching Project");
        return queryResult;
    }

    /**
     * Will not update status.
     */
    public void UpdateProjectInfo(Project project)//TODO Check input validity
    {
        Project currentProject=SelectByProjectId(project.getProjectId());
        if(StringUtils.isNotEmpty(project.getProjectName()))
            currentProject.setProjectName(project.getProjectName());
        if(StringUtils.isNotEmpty(project.getReferredOuterCustomerId()))
            currentProject.setReferredOuterCustomerId(project.getReferredOuterCustomerId());
        if(ObjectUtils.isNotEmpty(project.getScheduledStartTime()))
            currentProject.setScheduledStartTime(project.getScheduledStartTime());
        if(ObjectUtils.isNotEmpty(project.getScheduledEndTime()))
            currentProject.setScheduledEndTime(project.getScheduledEndTime());
        if(StringUtils.isNotEmpty(project.getMileStone()))
            currentProject.setMileStone(project.getMileStone());
        if(StringUtils.isNotEmpty(project.getTechnology()))
            currentProject.setTechnology(project.getTechnology());
        if(ObjectUtils.isNotEmpty(project.getReferredBusinessFieldId()))
            currentProject.setReferredBusinessFieldId(project.getReferredBusinessFieldId());
        if(StringUtils.isNotEmpty(project.getMainFunction()))
            currentProject.setMainFunction(project.getMainFunction());

        projectMapper.updateByPrimaryKey(project);
    }
    /**
     * Block before QA Manager, EPG Leader, Conf. Manager
     */
    private boolean additionalProjectStateTransitionCheck(String projectId,ProjectStateTransition.ProjectState currentState)
    {
        if(currentState!=ProjectStateTransition.ProjectState.Initiated)
            return true;
        List<ProjectMiscellaneous> projectMiscs=projectMiscellaneousMapper.selectByProjectId(projectId);
        boolean qaAdded=false, epgAdded=false, confAdded=false;
        for(ProjectMiscellaneous item: projectMiscs)//TODO Check role name in the end
        {
            if(item.getKeyField()
                    .equals("QAManager"))
                qaAdded=true;
            if(item.getKeyField()
                    .equals("EPGManager"))
                epgAdded=true;
            if(item.getKeyField()
                    .equals("ConfigurationManager"))
                confAdded=true;
        }
        return qaAdded&&epgAdded&&confAdded;
    }

    public void UpdateProjectStatus(String projectId,String status)
    {
        ProjectStateTransition.ProjectState state=ProjectStateTransition.ProjectState.valueOf(status);

        Project project=SelectByProjectId(projectId);
        String currentState=project.getStatus();
        ProjectStateTransition transition=ProjectStateTransition.getInstance();

        boolean isValidTransition=transition.isValidTransition(ProjectStateTransition.ProjectState.valueOf(currentState),
                                                               ProjectStateTransition.ProjectState.valueOf(status));
        if(!isValidTransition&&!additionalProjectStateTransitionCheck(projectId,
                                                                      ProjectStateTransition.ProjectState.valueOf(currentState)))
            throw new IllegalArgumentException("Invalid transition from: "+currentState+" to: "+status);

        Project toUpdateProject=new Project();
        toUpdateProject.setProjectId(projectId);
        toUpdateProject.setStatus(ProjectStateTransition.ProjectState.valueOf(status));

        projectMapper.updateByPrimaryKeySelective(project);
    }

    public void UpdateProjectMiscWhenMemberUpdated(String projectId,String global_role_name)
    {
        var toInsert=new ProjectMiscellaneous(snowFlakeIdGenerator.getNextId(),
                                              projectId,
                                              global_role_name,
                                              "MemberAdded");
        projectMiscellaneousMapper.insert(toInsert);
    }

}
