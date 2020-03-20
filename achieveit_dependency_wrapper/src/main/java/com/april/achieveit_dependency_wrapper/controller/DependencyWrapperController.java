package com.april.achieveit_dependency_wrapper.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path="/")
public class DependencyWrapperController
{
    private static Logger logger=LoggerFactory.getLogger(DependencyWrapperController.class);

    @PostMapping(path="/email")
    public ResponseContent EmailService(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        result.setMessage("Mails have been sent.");

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PostMapping(path="/emailList")
    public ResponseContent EmailListService(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();


        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PostMapping(path="/git")
    public ResponseContent GitService(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();


        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PostMapping(path="/fileServer")
    public ResponseContent FileServerService(@RequestBody Map<String,String> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();


        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }


}
