package com.april.achieveit_userinfo.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_userinfo_interface.api.LoginServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path="/user")
public class LoginController implements LoginServiceApi
{
    //TODO Placeholder

    private static Logger logger=LoggerFactory.getLogger(LoginController.class);

    @Override
    @PostMapping(path="/login")
    public ResponseContent Login(@RequestBody Map<String,String> params)
    {
        return null;
    }

    @Override
    @PostMapping(path="/info")
    public ResponseContent UpdateCredential(@RequestBody Map<String,String> params)
    {
        return null;
    }

}
