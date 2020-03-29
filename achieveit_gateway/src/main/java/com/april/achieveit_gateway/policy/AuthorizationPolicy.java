package com.april.achieveit_gateway.policy;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_gateway.client.RoleServiceClient;
import com.april.achieveit_gateway.utility.RereadableRequestWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthorizationPolicy
{
    private Logger logger=LoggerFactory.getLogger(AuthorizationPolicy.class);

    @Autowired
    UrlPermissionVerificationHelper urlPermissionVerificationHelper;

    @Autowired
    RoleServiceClient roleServiceClient;

    @Autowired
    ObjectMapper objectMapper;

    private List<String> queryUserPermission(String projectId,String userId)
    {
        Map<String,String> queryParams=new HashMap<>()
        {{
            put("user_id",
                userId);
        }};
        if(projectId!=null)
            queryParams.put("project_id",
                            projectId);
        ResponseContent permissionResponse=roleServiceClient.GetUserPermission(queryParams);
        if(permissionResponse.getResult()==null)
            return null;
        else
            return objectMapper.convertValue(permissionResponse.getResult(),
                                             new TypeReference<List<String>>()
                                             {
                                             });
    }

    private List<String> queryUserGlobalPermission(String userId)
    {
        return queryUserPermission(null,
                                   userId);
    }

    private List<String> queryUserProjectPermission(String projectId,String userId)
    {
        return queryUserPermission(projectId,
                                   userId);
    }

    public void Verify(String userId,RereadableRequestWrapper request) throws Exception
    {
        String requestMethod=request.getMethod();
        String requestPath=request.getRequestURI();

        //! Global permission is fallback of project permission in case some query requiring project_id may not be found;

        Map<String,String> currentRequestUrlProperty=urlPermissionVerificationHelper.getCurrentRequestPathProperty(requestPath,
                                                                                                                   requestMethod);
        if(currentRequestUrlProperty==null)
            throw new IllegalAccessException("Invalid request url.");
        if(currentRequestUrlProperty.get("permission")
                .equals(""))
            return;

        //Get global permission
        List<String> globalPermission=queryUserGlobalPermission(userId);
        if(globalPermission!=null&&globalPermission.contains(currentRequestUrlProperty.get("permission")))
            return;//Short circuit when global permission meets the requirement

        String requestBody=new String(request.getBody());

        //Get project permission
        String projectId=urlPermissionVerificationHelper.ParseProjectId(requestPath,
                                                                        requestMethod,
                                                                        requestBody);

        List<String> projectPermission=queryUserProjectPermission(projectId,
                                                                  userId);
        if(projectPermission!=null&&projectPermission.contains(currentRequestUrlProperty.get("permission")))
            return;

        throw new IllegalAccessException("No permission.");
    }
}


