package com.april.achieveit_userinfo.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_common.utility.CookieUtility;
import com.april.achieveit_common.utility.JWTUtility;
import com.april.achieveit_userinfo.service.AuthenticationService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(path="/")
public class LoginController
{
    private static Logger logger=LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @SneakyThrows
    @PostMapping(path="/login")
    public ResponseContent Login(@RequestBody Map<String,String> params,HttpServletRequest request,HttpServletResponse response)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent content=new ResponseContent();
        String username=params.get("username");
        String password=params.get("password");
        String jwt=authenticationService.Login(username,
                                               password);

        content.setResult(new ImmutablePair<>("JWT",
                                              jwt));
        content.setStatus(ResponseContentStatus.SUCCESS);
        CookieUtility.CreateCookieBuilder(response)
                .request(request)
                .build("JWT",
                       jwt);
        return content;
    }

    @SneakyThrows
    @PutMapping(path="/info")
    public ResponseContent UpdateCredential(@RequestBody Map<String,String> params,HttpServletRequest request,HttpServletResponse response)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent content=new ResponseContent();
        String newPassword=params.get("user_password");
        String originalPassword=params.get("original_password");
        String jwt=CookieUtility.getCookieValue(request,
                                                "JWT");
        String userId=JWTUtility.getSubjectFromJWT(jwt);
        authenticationService.UpdateUserInfo(userId,originalPassword,newPassword);

       return RenewToken(request,response);
    }

    @SneakyThrows
    @PostMapping(path="/renewToken")
    public ResponseContent RenewToken(HttpServletRequest request,HttpServletResponse response)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent content=new ResponseContent();

        String jwt=CookieUtility.getCookieValue(request,
                                             "JWT",
                                             null);
        String renewedToken=authenticationService.RenewToken(jwt);

        content.setStatus(ResponseContentStatus.SUCCESS);
        content.setResult(new ImmutablePair<>("JWT",
                                              renewedToken));
        CookieUtility.CreateCookieBuilder(response)
                .request(request)
                .build("JWT",
                       renewedToken);
        return content;
    }

}
