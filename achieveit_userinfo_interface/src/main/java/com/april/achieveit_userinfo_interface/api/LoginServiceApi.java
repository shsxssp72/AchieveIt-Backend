package com.april.achieveit_userinfo_interface.api;

import com.april.achieveit_common.bean.ResponseContent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginServiceApi
{
    @PostMapping(path="/login")
    ResponseContent Login(@RequestBody Map<String,String> params);

    @PostMapping(path="/info")
    ResponseContent UpdateCredential(@RequestBody Map<String,String> params);

    @PostMapping(path="/renewToken")
    ResponseContent RenewToken(HttpServletRequest request);
}
