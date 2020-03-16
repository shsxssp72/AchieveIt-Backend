package com.april.achieveit_userinfo.service;

import com.april.achieveit_userinfo.mapper.*;
import com.april.achieveit_userinfo_interface.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorizationService
{
    private Logger logger=LoggerFactory.getLogger(AuthorizationService.class);
    @Autowired
    ProjectRoleMapper projectRoleMapper;
    @Autowired
    GlobalRoleMapper globalRoleMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    ProjectUserRelationMapper projectUserRelationMapper;
    @Autowired
    ProjectUserPermissionRelationMapper userPermissionRelationMapper;

    @Autowired
    ProjectRolePermissionRelationMapper projectRolePermissionRelationMapper;
    @Autowired
    GlobalRolePermissionRelationMapper globalRolePermissionRelationMapper;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

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

    public List<String> GetUserPermissionName(String projectId,String userId)
    {
        List<ProjectUserPermissionRelation> projectUserRelations=userPermissionRelationMapper.selectByProjectIdAndUserId(projectId,
                                                                                                                         userId);
        return projectUserRelations.parallelStream()
                .map(i->permissionMapper.selectByPrimaryKey(i.getReferredPermissionId())
                        .getPermissionName())
                .collect(Collectors.toList());
    }

    public void UpdateUserProjectRole(String projectId,String userId,List<Map<String,String>> projectRoleIdList)
    {
        projectUserRelationMapper.deleteByProjectIdAndUserId(projectId,
                                                             userId);
        for(Map<String,String> item: projectRoleIdList)
        {
            projectUserRelationMapper.insert(new ProjectUserRelation(projectId,
                                                                     userId,
                                                                     item.get("superior_id"),
                                                                     Long.parseLong(item.get("project_role_id"))));
        }
    }

    public void UpdateProjectMember(String projectId,List<Map<String,String>> members) throws JsonProcessingException
    {
        projectUserRelationMapper.deleteByProjectIdAndUserId(projectId,
                                                             null);//Delete all under the project
        for(Map<String,String> member: members)
        {
            String userId=member.get("user_id");
            List<Map<String,String>> projectRoleIdList=objectMapper.readValue(member.get("project_role_id_list"),
                                                                              new TypeReference<List<Map<String,String>>>()
                                                                              {
                                                                              });
            for(Map<String,String> item: projectRoleIdList)
            {
                long projectRoleId=Long.parseLong(item.get("project_role_id"));
                String superiorId=item.get("superior_id");
                projectUserRelationMapper.insert(new ProjectUserRelation(projectId,
                                                                         userId,
                                                                         superiorId,
                                                                         projectRoleId));
            }
        }
    }

    public void UpdateUserProjectPermission(String projectId,String userId,List<String> permissionList)
    {
        userPermissionRelationMapper.deleteByProjectIdAndUserId(projectId,userId);
        for(String item:permissionList)
        {
            Permission permission=permissionMapper.selectByPermissionName(item);
            userPermissionRelationMapper.insert(new ProjectUserPermissionRelation(projectId,userId,permission.getPermissionId()));
        }
    }
}
