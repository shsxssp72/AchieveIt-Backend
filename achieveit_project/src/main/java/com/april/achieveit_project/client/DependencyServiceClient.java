package com.april.achieveit_project.client;

import com.april.achieveit_common.bean.ResponseContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("dependency-service")
public interface DependencyServiceClient
{
    @PostMapping(path="/email")
    ResponseContent EmailService(@RequestBody Map<String,String> params);

    @PostMapping(path="/emailList")
    ResponseContent EmailListService(@RequestBody Map<String,String> params);

    @PostMapping(path="/git")
    ResponseContent GitService(@RequestBody Map<String,String> params);

    @PostMapping(path="/fileServer")
    ResponseContent FileServerService(@RequestBody Map<String,String> params);
}
