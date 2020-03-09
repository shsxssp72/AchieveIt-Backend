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

    public interface AdvancedViewLevel extends BasicViewLevel
    {
    }

    public interface AuthenticatedViewLevel extends AdvancedViewLevel
    {
    }

    public interface SensitiveViewLevel extends AuthenticatedViewLevel
    {
    }

    public interface AuthorizedViewLevel extends SensitiveViewLevel
    {
    }

    public interface ConfidentialViewLevel extends AuthorizedViewLevel
    {
    }

    public interface SystemViewLevel extends ConfidentialViewLevel
    {
    }
}
