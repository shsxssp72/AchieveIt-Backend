package com.april.achieveit_project.client;

import com.april.achieveit_userinfo_interface.api.RoleServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("userinfo-service")
public interface RoleServiceClient extends RoleServiceApi
{
}
