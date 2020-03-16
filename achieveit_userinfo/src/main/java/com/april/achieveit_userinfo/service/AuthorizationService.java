package com.april.achieveit_userinfo.service;

import com.april.achieveit_userinfo.mapper.*;
import com.april.achieveit_userinfo_interface.entity.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorizationService
{
    private Logger logger=LoggerFactory.getLogger(AuthorizationService.class);
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
    private ObjectMapper objectMapper;

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

    @SneakyThrows
    private Map<String,String> prepareUserProjectRole(String projectId,String userId,List<ProjectUserRelation> projectUserRelations)
    {
        List<Map<String,String>> roleIdList=new LinkedList<>();
        for(ProjectUserRelation item: projectUserRelations)
        {
            ProjectRole projectRole=projectRoleMapper.selectByPrimaryKey(item.getReferredProjectRoleId());

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
    public Map<String,String> GetUserProjectRole(String projectId,String userId)
    {
        List<ProjectUserRelation> projectUserRelations=projectUserRelationMapper.selectByProjectIdAndUserId(projectId,
                                                                                                            userId);
        return prepareUserProjectRole(projectId,
                                      userId,
                                      projectUserRelations);
    }

    public List<Map<String,String>> GetUserRoleFromMultipleProject(String userId)
    {
        List<ProjectUserRelation> projectUserRelations=projectUserRelationMapper.selectByProjectIdAndUserId(null,
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

    public List<Map<String,String>> BatchGetUserRoleFromMultipleProject(List<String> userIds)
    {
        List<Map<String,String>> result=new LinkedList<>();
        for(var userId: userIds)
        {
            result.addAll(GetUserRoleFromMultipleProject(userId));
        }
        return result;
    }

    @Transactional
    public List<Map<String,String>> GetProjectMember(String projectId)
    {
        List<Map<String,String>> result=new LinkedList<>();

        List<ProjectUserRelation> projectUserRelations=projectUserRelationMapper.selectByProjectIdAndUserId(projectId,
                                                                                                            null);
        for(ProjectUserRelation item: projectUserRelations)
        {
            ProjectRole projectRole=projectRoleMapper.selectByPrimaryKey(item.getReferredProjectRoleId());

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

    private UserInfo GetUserInfoById(String userId)
    {
        return userInfoMapper.selectByPrimaryKey(userId);
    }

    public Map<String,String> GetUserGlobalRole(String userId)
    {
        Long globalRoleId=GetUserInfoById(userId).getReferredUserGlobalRoleId();
        String globalRoleName=globalRoleMapper.selectByPrimaryKey(globalRoleId)
                .getGlobalRoleName();
        return Map.of("global_role_id",
                      String.valueOf(globalRoleId),
                      "global_role_name",
                      globalRoleName);
    }

    public List<String> GetInferior(String superiorId)
    {
        List<ProjectUserRelation> userRelations=projectUserRelationMapper.selectBySuperiorId(superiorId);
        return userRelations.parallelStream()
                .map(ProjectUserRelation::getReferredUserId)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<String> GetUserPermissionName(String projectId,String userId)
    {
        List<ProjectUserPermissionRelation> projectUserRelations=userPermissionRelationMapper.selectByProjectIdAndUserId(projectId,
                                                                                                                         userId);
        return projectUserRelations.parallelStream()
                .filter(i->i.getPermitWeight()>0)
                .map(i->permissionMapper.selectByPrimaryKey(i.getReferredPermissionId())
                        .getPermissionName())
                .collect(Collectors.toList());
    }

    /**
     * For multiple role may have same permission
     */
    private void deregisterProjectRole(String projectId,String userId,Long projectRoleId)
    {
        //
        List<Long> roleRelatedPermissionIds=projectRolePermissionRelationMapper.selectByProjectRoleId(projectRoleId)
                .parallelStream()
                .map(ProjectRolePermissionRelation::getReferredPermissionId)
                .collect(Collectors.toList());
        for(Long permissionId: roleRelatedPermissionIds)
        {
            ProjectUserPermissionRelation currentUserPermission=userPermissionRelationMapper.selectByProjectIdAndUserIdAndPermissionId(projectId,
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
    private void registerProjectRole(String projectId,String userId,Long projectRoleId)
    {
        List<Long> roleRelatedPermissionIds=projectRolePermissionRelationMapper.selectByProjectRoleId(projectRoleId)
                .parallelStream()
                .map(ProjectRolePermissionRelation::getReferredPermissionId)
                .collect(Collectors.toList());
        for(Long permissionId: roleRelatedPermissionIds)
        {
            ProjectUserPermissionRelation currentUserPermission=userPermissionRelationMapper.selectByProjectIdAndUserIdAndPermissionId(projectId,
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
    public void UpdateUserProjectRole(String projectId,String userId,List<Map<String,String>> projectRoleIdList)
    {
        List<ProjectUserRelation> currentUserProjectRelations=projectUserRelationMapper.selectByProjectIdAndUserId(projectId,
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
    public void UpdateProjectMember(String projectId,List<Map<String,String>> members)
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
    public void UpdateUserProjectPermission(String projectId,String userId,List<String> permissionList)
    {
        for(String item: EDITABLE_PERMISSIONS)
        {
            Long permissionId=permissionMapper.selectByPermissionName(item)
                    .getPermissionId();
            userPermissionRelationMapper.deleteByProjectIdAndUserIdAndPermissionId(projectId,
                                                                                   userId,
                                                                                   permissionId);
        }
        permissionList.retainAll(EDITABLE_PERMISSIONS);
        for(String item: permissionList)
        {
            Permission permission=permissionMapper.selectByPermissionName(item);
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
