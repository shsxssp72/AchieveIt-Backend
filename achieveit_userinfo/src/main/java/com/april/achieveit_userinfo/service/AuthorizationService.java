package com.april.achieveit_userinfo.service;

import com.april.achieveit_common.utility.RedisCacheUtility;
import com.april.achieveit_userinfo.mapper.*;
import com.april.achieveit_userinfo_interface.entity.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class AuthorizationService extends RedisCacheUtility.AbstractRedisCacheService
{
    private Logger logger=LoggerFactory.getLogger(AuthorizationService.class);

    static
    {
        for(Method method: AuthorizationService.class.getDeclaredMethods())
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
    private ProjectRoleMapper projectRoleMapper;
    @Autowired
    private GlobalRoleMapper globalRoleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ProjectUserRelationMapper projectUserRelationMapper;
    @Autowired
    private ProjectUserPermissionRelationMapper userPermissionRelationMapper;

    @Autowired
    private ProjectRolePermissionRelationMapper projectRolePermissionRelationMapper;
    @Autowired
    private GlobalRolePermissionRelationMapper globalRolePermissionRelationMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Value("${local.editable-permission}")
    private String editable_permission_string;
    private Set<String> EDITABLE_PERMISSIONS;

    @PostConstruct
    private void Init()
    {
        EDITABLE_PERMISSIONS=Set.of(editable_permission_string.split(","));
    }

    private ProjectRole selectProjectRoleByPrimaryKey(Long projectRoleId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<ProjectRole>(redisTemplate,
                                                                                 objectMapper,
                                                                                 reentrantLocks.get(currentMethodName),
                                                                                 cacheValidTime,
                                                                                 cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+projectRoleId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->projectRoleMapper.selectByPrimaryKey(projectRoleId));
    }

    private List<ProjectUserRelation> selectProjectUserRelationByProjectIdAndUserId(String projectId,String userId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<List<ProjectUserRelation>>(redisTemplate,
                                                                                               objectMapper,
                                                                                               reentrantLocks.get(currentMethodName),
                                                                                               cacheValidTime,
                                                                                               cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+projectId+"_"+userId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->projectUserRelationMapper.selectByProjectIdAndUserId(projectId,
                                                                                                         userId));
    }

    private GlobalRole selectGlobalRoleByPrimaryKey(Long globalRoleId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<GlobalRole>(redisTemplate,
                                                                                objectMapper,
                                                                                reentrantLocks.get(currentMethodName),
                                                                                cacheValidTime,
                                                                                cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+globalRoleId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->globalRoleMapper.selectByPrimaryKey(globalRoleId));
    }

    private List<ProjectUserRelation> selectUserRelationBySuperiorId(String projectId,String superiorId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<List<ProjectUserRelation>>(redisTemplate,
                                                                                               objectMapper,
                                                                                               reentrantLocks.get(currentMethodName),
                                                                                               cacheValidTime,
                                                                                               cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+projectId+"_"+superiorId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->projectUserRelationMapper.selectBySuperiorId(projectId,
                                                                                                 superiorId));
    }

    private List<ProjectUserPermissionRelation> selectUserPermissionRelationByProjectIdAndUserId(String projectId,String userId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<List<ProjectUserPermissionRelation>>(redisTemplate,
                                                                                                         objectMapper,
                                                                                                         reentrantLocks.get(currentMethodName),
                                                                                                         cacheValidTime,
                                                                                                         cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+projectId+"_"+userId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->userPermissionRelationMapper.selectByProjectIdAndUserId(projectId,
                                                                                                            userId));
    }

    private Permission selectPermissionByPrimaryKey(Long permissionId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<Permission>(redisTemplate,
                                                                                objectMapper,
                                                                                reentrantLocks.get(currentMethodName),
                                                                                cacheValidTime,
                                                                                cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+permissionId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->permissionMapper.selectByPrimaryKey(permissionId));
    }

    private ProjectUserPermissionRelation selectUserPermissionRelationByProjectIdAndUserIdAndPermissionId(String projectId,String userId,Long permissionId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<ProjectUserPermissionRelation>(redisTemplate,
                                                                                                   objectMapper,
                                                                                                   reentrantLocks.get(currentMethodName),
                                                                                                   cacheValidTime,
                                                                                                   cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+projectId+"_"+userId+"_"+permissionId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->userPermissionRelationMapper.selectByProjectIdAndUserIdAndPermissionId(projectId,
                                                                                                                           userId,
                                                                                                                           permissionId));
    }

    private List<ProjectRolePermissionRelation> selectProjectRolePermissionRelationByProjectRoleId(Long projectRoleId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<List<ProjectRolePermissionRelation>>(redisTemplate,
                                                                                                         objectMapper,
                                                                                                         reentrantLocks.get(currentMethodName),
                                                                                                         cacheValidTime,
                                                                                                         cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+projectRoleId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->projectRolePermissionRelationMapper.selectByProjectRoleId(projectRoleId));
    }

    private Permission selectPermissionByPermissionName(String permissionName)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<Permission>(redisTemplate,
                                                                                objectMapper,
                                                                                reentrantLocks.get(currentMethodName),
                                                                                cacheValidTime,
                                                                                cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+permissionName;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->permissionMapper.selectByPermissionName(permissionName));
    }

    @SneakyThrows
    private Map<String,String> prepareUserProjectRole(@NotNull String projectId,@NotNull String userId,List<ProjectUserRelation> projectUserRelations)
    {
        List<Map<String,String>> roleIdList=new LinkedList<>();
        for(ProjectUserRelation item: projectUserRelations)
        {
            ProjectRole projectRole=selectProjectRoleByPrimaryKey(item.getReferredProjectRoleId());

            roleIdList.add(Map.of("project_role_id",
                                  String.valueOf(item.getReferredProjectRoleId()),
                                  "project_role_name",
                                  projectRole.getProjectRoleName(),
                                  "superior_id",
                                  item.getReferredSuperiorId()));
        }

        return Map.of("project_id",
                      projectId,
                      "user_id",
                      userId,
                      "project_role_id_list",
                      objectMapper.writeValueAsString(roleIdList));
    }


    @SneakyThrows
    public Map<String,String> GetUserProjectRole(@NotNull String projectId,@NotNull String userId)
    {
        List<ProjectUserRelation> projectUserRelations=selectProjectUserRelationByProjectIdAndUserId(projectId,
                                                                                                     userId);
        return prepareUserProjectRole(projectId,
                                      userId,
                                      projectUserRelations);
    }

    public List<Map<String,String>> GetUserRoleFromMultipleProject(@NotNull String userId)
    {
        List<ProjectUserRelation> projectUserRelations=selectProjectUserRelationByProjectIdAndUserId(null,
                                                                                                     userId);
        Map<String,LinkedList<ProjectUserRelation>> classifiedProjectUserRelation=new HashMap<>();
        for(ProjectUserRelation item: projectUserRelations)
        {
            if(!classifiedProjectUserRelation.containsKey(item.getReferredProjectId()))
                classifiedProjectUserRelation.put(item.getReferredProjectId(),
                                                  new LinkedList<>()
                                                  {{
                                                      add(item);
                                                  }});
            else
                classifiedProjectUserRelation.get(item.getReferredProjectId())
                        .add(item);
        }

        List<Map<String,String>> result=new LinkedList<>();
        for(Map.Entry<String,LinkedList<ProjectUserRelation>> entry: classifiedProjectUserRelation.entrySet())
        {
            result.add(prepareUserProjectRole(entry.getKey(),
                                              userId,
                                              entry.getValue()));
        }
        return result;
    }

    public List<Map<String,String>> BatchGetUserRoleFromMultipleProject(@NotNull List<String> userIds)
    {
        List<Map<String,String>> result=new LinkedList<>();
        for(var userId: userIds)
        {
            result.addAll(GetUserRoleFromMultipleProject(userId));
        }
        return result;
    }

    @Transactional
    public List<Map<String,String>> GetProjectMember(@NotNull String projectId)
    {
        List<Map<String,String>> result=new LinkedList<>();

        List<ProjectUserRelation> projectUserRelations=selectProjectUserRelationByProjectIdAndUserId(projectId,
                                                                                                     null);
        for(ProjectUserRelation item: projectUserRelations)
        {
            ProjectRole projectRole=selectProjectRoleByPrimaryKey(item.getReferredProjectRoleId());

            result.add(Map.of("user_id",
                              item.getReferredUserId(),
                              "project_role_id",
                              String.valueOf(item.getReferredProjectRoleId()),
                              "superior_id",
                              item.getReferredSuperiorId(),
                              "project_role_name",
                              projectRole.getProjectRoleName()));
        }
        return result;
    }

    private UserInfo getUserInfoById(@NotNull String userId)
    {
        String currentMethodName=Thread.currentThread()
                .getStackTrace()[1].getMethodName();
        var redisCacheHelper=new RedisCacheUtility.RedisCacheHelper<UserInfo>(redisTemplate,
                                                                              objectMapper,
                                                                              reentrantLocks.get(currentMethodName),
                                                                              cacheValidTime,
                                                                              cacheConcurrentWaitTime);

        String redisKey=currentMethodName+"_"+userId;
        return redisCacheHelper.QueryUsingCache(redisKey,
                                                ()->userInfoMapper.selectByPrimaryKey(userId));
    }


    public Map<String,String> GetUserGlobalRole(String userId)
    {
        Long globalRoleId=getUserInfoById(userId).getReferredUserGlobalRoleId();
        String globalRoleName=selectGlobalRoleByPrimaryKey(globalRoleId).getGlobalRoleName();
        return Map.of("global_role_id",
                      String.valueOf(globalRoleId),
                      "global_role_name",
                      globalRoleName);
    }


    public List<String> GetInferior(String projectId,@NotNull String superiorId)
    {
        List<ProjectUserRelation> userRelations=selectUserRelationBySuperiorId(projectId,
                                                                               superiorId);
        return userRelations.parallelStream()
                .map(ProjectUserRelation::getReferredUserId)
                .collect(Collectors.toList());
    }


    /**
     * When passing projectId as null, query global permission
     */
    @Transactional
    public List<String> GetUserPermissionName(String projectId,@NotNull String userId)
    {
        List<ProjectUserPermissionRelation> projectUserRelations=selectUserPermissionRelationByProjectIdAndUserId(projectId,
                                                                                                                  userId);
        return projectUserRelations.parallelStream()
                .filter(i->i.getPermitWeight()>0)
                .map(i->selectPermissionByPrimaryKey(i.getReferredPermissionId()).getPermissionName())
                .collect(Collectors.toList());
    }


    /**
     * For multiple role may have same permission
     */
    private void deregisterProjectRole(@NotNull String projectId,@NotNull String userId,Long projectRoleId)
    {
        //
        List<Long> roleRelatedPermissionIds=selectProjectRolePermissionRelationByProjectRoleId(projectRoleId).parallelStream()
                .map(ProjectRolePermissionRelation::getReferredPermissionId)
                .collect(Collectors.toList());
        for(Long permissionId: roleRelatedPermissionIds)
        {
            ProjectUserPermissionRelation currentUserPermission=selectUserPermissionRelationByProjectIdAndUserIdAndPermissionId(projectId,
                                                                                                                                userId,
                                                                                                                                permissionId);
            userPermissionRelationMapper.updateWeightByProjectIdAndUserIdAndPermissionId(currentUserPermission.getPermitWeight()-1,
                                                                                         projectId,
                                                                                         userId,
                                                                                         permissionId);
        }
    }

    /**
     * For multiple role may have same permission
     */
    private void registerProjectRole(@NotNull String projectId,@NotNull String userId,Long projectRoleId)
    {
        List<Long> roleRelatedPermissionIds=selectProjectRolePermissionRelationByProjectRoleId(projectRoleId).parallelStream()
                .map(ProjectRolePermissionRelation::getReferredPermissionId)
                .collect(Collectors.toList());
        for(Long permissionId: roleRelatedPermissionIds)
        {
            ProjectUserPermissionRelation currentUserPermission=selectUserPermissionRelationByProjectIdAndUserIdAndPermissionId(projectId,
                                                                                                                                userId,
                                                                                                                                permissionId);
            if(currentUserPermission!=null)
                userPermissionRelationMapper.updateWeightByProjectIdAndUserIdAndPermissionId(currentUserPermission.getPermitWeight()+1,
                                                                                             projectId,
                                                                                             userId,
                                                                                             permissionId);
            else
                userPermissionRelationMapper.insert(new ProjectUserPermissionRelation(projectId,
                                                                                      userId,
                                                                                      permissionId,
                                                                                      1));
        }
    }

    @Transactional
    public void UpdateUserProjectRole(@NotNull String projectId,@NotNull String userId,List<Map<String,String>> projectRoleIdList)
    {
        List<ProjectUserRelation> currentUserProjectRelations=selectProjectUserRelationByProjectIdAndUserId(projectId,
                                                                                                            userId);
        for(ProjectUserRelation relation: currentUserProjectRelations)
        {
            deregisterProjectRole(projectId,
                                  userId,
                                  relation.getReferredProjectRoleId());
        }
        projectUserRelationMapper.deleteByProjectIdAndUserId(projectId,
                                                             userId);

        for(Map<String,String> item: projectRoleIdList)
        {
            long projectRoleId=Long.parseLong(item.get("project_role_id"));
            projectUserRelationMapper.insert(new ProjectUserRelation(projectId,
                                                                     userId,
                                                                     item.get("superior_id"),
                                                                     projectRoleId));
            registerProjectRole(projectId,
                                userId,
                                projectRoleId);
        }
    }

    @SneakyThrows
    @Transactional
    public void UpdateProjectMember(@NotNull String projectId,List<Map<String,String>> members)
    {
        for(Map<String,String> member: members)
        {
            String userId=member.get("user_id");
            List<Map<String,String>> projectRoleIdList=objectMapper.readValue(member.get("project_role_id_list"),
                                                                              new TypeReference<List<Map<String,String>>>()
                                                                              {
                                                                              });
            UpdateUserProjectRole(projectId,
                                  userId,
                                  projectRoleIdList);
        }
    }

    @Transactional
    public void UpdateUserProjectPermission(@NotNull String projectId,@NotNull String userId,List<String> permissionList)
    {
        for(String item: EDITABLE_PERMISSIONS)
        {
            Long permissionId=selectPermissionByPermissionName(item).getPermissionId();
            userPermissionRelationMapper.deleteByProjectIdAndUserIdAndPermissionId(projectId,
                                                                                   userId,
                                                                                   permissionId);
        }
        permissionList.retainAll(EDITABLE_PERMISSIONS);
        for(String item: permissionList)
        {
            Permission permission=selectPermissionByPermissionName(item);
            userPermissionRelationMapper.insert(new ProjectUserPermissionRelation(projectId,
                                                                                  userId,
                                                                                  permission.getPermissionId(),
                                                                                  1));
        }
    }

    public Set<String> getEditablePermissions()
    {
        return EDITABLE_PERMISSIONS;
    }
}
