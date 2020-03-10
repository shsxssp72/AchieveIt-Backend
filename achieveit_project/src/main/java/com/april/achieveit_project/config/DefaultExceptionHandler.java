package com.april.achieveit_project.config;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler
{
    @ExceptionHandler(value=Exception.class)
    public ResponseContent HandleGeneralException(Exception e)
    {
        ResponseContent result=new ResponseContent();
        result.setStatus(ResponseContentStatus.FAILURE);
        result.setMessage(e.getMessage());
        return result;
    }
}
