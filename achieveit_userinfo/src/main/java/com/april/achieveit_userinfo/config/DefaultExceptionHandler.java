package com.april.achieveit_userinfo.config;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler
{
    private static Logger logger=LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(value=Exception.class)
    public ResponseContent HandleGeneralException(Exception e)
    {
        ResponseContent result=new ResponseContent();
        result.setStatus(ResponseContentStatus.FAILURE);
        result.setMessage(e.getMessage());
        logger.warn("Error occurred: "+e.getMessage());
        return result;
    }
}
