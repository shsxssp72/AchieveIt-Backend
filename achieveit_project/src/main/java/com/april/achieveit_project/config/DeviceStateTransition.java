package com.april.achieveit_project.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DeviceStateTransition
{
    public enum DeviceState
    {
        Available,LentOut,ToBeChecked,Maintaining,Scrapped;

        @Override
        public String toString()
        {
            return getClass().getName()+"::"+this.name();
        }
    }

    private Map<String,Set<String>> validTransition=new HashMap<>()
    {{
        put(DeviceState.Available.name(),
            Set.of(DeviceState.LentOut.name(),
                   DeviceState.Scrapped.name(),
                   DeviceState.Maintaining.name()));
        put(DeviceState.LentOut.name(),
            Set.of(DeviceState.ToBeChecked.name()));
        put(DeviceState.ToBeChecked.name(),
            Set.of(DeviceState.Maintaining.name(),
                   DeviceState.Available.name()));
        put(DeviceState.Maintaining.name(),
            Set.of(DeviceState.Available.name(),
                   DeviceState.Scrapped.name()));
        put(DeviceState.Scrapped.name(),
            Set.of());
    }};

    private static class Holder
    {
        private static DeviceStateTransition instance=new DeviceStateTransition();
    }

    private DeviceStateTransition()
    {
    }

    public static DeviceStateTransition getInstance()
    {
        return Holder.instance;
    }

    public boolean isValidTransition(DeviceState currentState,DeviceState nextState) throws IllegalArgumentException
    {
        if(!validTransition.containsKey(currentState.name())||!validTransition.containsKey(nextState.name()))
            throw new IllegalArgumentException("Not a valid state name");
        return validTransition.get(currentState.name())
                .contains(nextState.name());
    }

}
