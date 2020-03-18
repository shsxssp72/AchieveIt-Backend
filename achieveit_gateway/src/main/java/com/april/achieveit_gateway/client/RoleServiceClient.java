package com.april.achieveit_gateway.client;

import com.april.achieveit_userinfo_interface.api.RoleServiceApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("userinfo-service")
public interface RoleServiceClient extends RoleServiceApi
{
}
