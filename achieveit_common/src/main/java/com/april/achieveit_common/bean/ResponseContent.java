package com.april.achieveit_common.bean;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import com.april.achieveit_common.utility.JsonVisibilityLevel;

import java.io.Serializable;

@Data
public class ResponseContent implements Serializable
{
    @JsonView(JsonVisibilityLevel.BasicViewLevel.class)
    private ResponseContentStatus status;
    @JsonView(JsonVisibilityLevel.BasicViewLevel.class)
    private String message;
    @JsonView(JsonVisibilityLevel.BasicViewLevel.class)
    private Object result;
}

