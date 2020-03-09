package com.april.achieveit_common.bean;

public enum ResponseContentStatus
{
    SUCCESS("success"),
    FAILURE("failure");


    private final String description;
    private ResponseContentStatus(String description)
    {
        this.description=description;
    }

    @Override
    public String toString()
    {
        return getClass().getName()+"::"+this.description;
    }
}
