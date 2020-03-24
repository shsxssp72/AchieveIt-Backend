package com.april.achieveit_project.service;

import com.april.achieveit_common.utility.RedisCacheUtility;
import com.april.achieveit_common.utility.SnowFlakeIdGenerator;
import com.april.achieveit_project.config.ProjectStateTransition;
import com.april.achieveit_project.entity.Project;
import com.april.achieveit_project.entity.ProjectMiscellaneous;
import com.april.achieveit_project.mapper.ProjectMapper;
import com.april.achieveit_project.mapper.ProjectMiscellaneousMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProjectService extends RedisCacheUtility.AbstractRedisCacheService
{
    private static Logger logger=LoggerFactory.getLogger(ProjectService.class);

    static
    {
        for(Method method: ProjectService.class.getDeclaredMethods())
        {

            reentrantLocks.computeIfAbsent(method.getName(),
                                           f->new ReentrantLock());
        }
    }

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${local.cache-valid-time}")
    private Integer cacheValidTime;
    @Value("${local.cache-concurrent-wait-time}")
    private Integer cacheConcurrentWaitTime;

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
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<List<Project>>(redisTemplate,
                                                                                   objectMapper,
                                                                                   reentrantLocks.get(currentMethodName),
                                                                                   cacheValidTime,
                                                                                   cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+projectName+"_"+pageSize+"_"+currentPage;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->
                                                {
                                                    PageHelper.startPage(currentPage,
                                                                         pageSize);
                                                    return projectMapper.selectByProjectNameAndStatus(projectName!=null?projectName:"",
                                                                                                      Set.of(ProjectStateTransition.ProjectState.Initiated.name(),
                                                                                                             ProjectStateTransition.ProjectState.Developing.name(),
                                                                                                             ProjectStateTransition.ProjectState.Delivered.name(),
                                                                                                             ProjectStateTransition.ProjectState.Finished.name(),
                                                                                                             ProjectStateTransition.ProjectState.ReadyArchive.name(),
                                                                                                             ProjectStateTransition.ProjectState.ArchiveDeclined.name(),
                                                                                                             ProjectStateTransition.ProjectState.Archived.name()));
                                                });
    }

    public List<Project> SelectByProjectIds(Set<String> projectIds,int pageSize,int currentPage)
    {
        if(projectIds==null||projectIds.isEmpty())
            return null;
        PageHelper.startPage(currentPage,
                             pageSize);
        return projectMapper.selectByProjectIds(projectIds);
    }

    private Project selectByProjectIdUsingCache(String projectId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<Project>(redisTemplate,
                                                                             objectMapper,
                                                                             reentrantLocks.get(currentMethodName),
                                                                             cacheValidTime,
                                                                             cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+projectId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->projectMapper.selectByPrimaryKey(projectId));
    }

    public Project SelectByProjectId(String projectId)
    {
        var queryResult=selectByProjectIdUsingCache(projectId);
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

        if(currentProject.getStatus()
                .equals(ProjectStateTransition.ProjectState.Rejected.name()))
            currentProject.setStatus(ProjectStateTransition.ProjectState.Applied);

        projectMapper.updateByPrimaryKey(project);
    }

    /**
     * Block before QA Manager, EPG Leader, Conf. Manager
     */
    private boolean additionalProjectStateTransitionCheck(String projectId,ProjectStateTransition.ProjectState currentState)
    {
        if(currentState!=ProjectStateTransition.ProjectState.Initiated)
            return true;

        boolean qaAdded=false, epgAdded=false, confAdded=false;
        //TODO Check role name in the end
        if(SelectMiscByProjectIdAndKey(projectId,
                                       "QAManager")!=null)
            qaAdded=true;
        if(SelectMiscByProjectIdAndKey(projectId,
                                       "EPGManager")!=null)
            epgAdded=true;
        if(SelectMiscByProjectIdAndKey(projectId,
                                       "ConfigurationManager")!=null)
            confAdded=true;

        return qaAdded&&epgAdded&&confAdded;
    }

    public void UpdateProjectStatus(String projectId,String status)//TODO Add Role Check
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
        InsertMiscByProjectIdAndKey(projectId,
                                    global_role_name,
                                    "MemberAdded");
    }

    public ProjectMiscellaneous SelectMiscByProjectIdAndKeyUsingCache(String projectId,String key)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<ProjectMiscellaneous>(redisTemplate,
                                                                                          objectMapper,
                                                                                          reentrantLocks.get(currentMethodName),
                                                                                          cacheValidTime,
                                                                                          cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+projectId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->projectMiscellaneousMapper.selectByProjectIdAndKey(projectId,
                                                                                                       key));
    }

    public String SelectMiscByProjectIdAndKey(String projectId,String key)
    {
        ProjectMiscellaneous queryResult=SelectMiscByProjectIdAndKeyUsingCache(projectId,
                                                                               key);
        return queryResult==null?null:queryResult.getValueField();
    }

    public void InsertMiscByProjectIdAndKey(String projectId,String key,String value)
    {
        ProjectMiscellaneous queryResult=SelectMiscByProjectIdAndKeyUsingCache(projectId,
                                                                               key);
        if(queryResult!=null)
            projectMiscellaneousMapper.deleteByPrimaryKey(queryResult.getMiscId());
        ProjectMiscellaneous misc=new ProjectMiscellaneous(snowFlakeIdGenerator.getNextId(),
                                                           projectId,
                                                           key,
                                                           value);
        projectMiscellaneousMapper.insert(misc);
    }

}
