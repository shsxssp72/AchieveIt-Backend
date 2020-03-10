package com.april.achieveit_project.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProjectStateTransition
{
    public enum ProjectState
    {
        Applied,Initiated,Rejected,Developing,Delivered,Finished,ReadyArchive,ArchiveDeclined,Archived;

        @Override
        public String toString()
        {
            return getClass().getName()+"::"+this.name();
        }

    }

    private Map<String,Set<String>> validTransition=new HashMap<>()
    {{
        put(ProjectState.Applied.name(),
            new HashSet<>()
            {{
                add(ProjectState.Initiated.name());
                add(ProjectState.Rejected.name());
            }});
        put(ProjectState.Initiated.name(),
            new HashSet<>()
            {{
                add(ProjectState.Developing.name());
            }});
        put(ProjectState.Rejected.name(),
            new HashSet<>()
            {{
                add(ProjectState.Applied.name());
            }});
        put(ProjectState.Developing.name(),
            new HashSet<>()
            {{
                add(ProjectState.Delivered.name());
            }});
        put(ProjectState.Delivered.name(),
            new HashSet<>()
            {{
                add(ProjectState.Finished.name());
            }});
        put(ProjectState.Finished.name(),
            new HashSet<>()
            {{
                add(ProjectState.ReadyArchive.name());
            }});
        put(ProjectState.ReadyArchive.name(),
            new HashSet<>()
            {{
                add(ProjectState.Archived.name());
                add(ProjectState.ArchiveDeclined.name());
            }});
        put(ProjectState.ArchiveDeclined.name(),
            new HashSet<>()
            {{
                add(ProjectState.ReadyArchive.name());
            }});
        put(ProjectState.Archived.name(),
            new HashSet<>());
    }};


    private static class Holder
    {
        private static ProjectStateTransition instance=new ProjectStateTransition();
    }

    private ProjectStateTransition()
    {
    }

    public static ProjectStateTransition getInstance()
    {
        return Holder.instance;
    }

    public boolean isValidTransition(ProjectState currentState,ProjectState nextState) throws IllegalArgumentException
    {
        if(!validTransition.containsKey(currentState.name())||!validTransition.containsKey(nextState.name()))
            throw new IllegalArgumentException("Not a valid state name");
        return validTransition.get(currentState.name())
                .contains(nextState.name());
    }


    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return getInstance();
    }

    private Object readResolve()
    {
        return getInstance();
    }

}
