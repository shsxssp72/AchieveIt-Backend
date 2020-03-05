package com.april.achieveit_common.utility;

import java.io.Serializable;

public class JsonVisibilityLevel implements Serializable
{
    public interface AbstractViewLevel
    {
    }

    public interface BasicViewLevel extends AbstractViewLevel
    {
    }

    public interface AuthenticatedViewLevel extends BasicViewLevel
    {
    }

    public interface SystemViewLevel extends AuthenticatedViewLevel
    {
    }
}
