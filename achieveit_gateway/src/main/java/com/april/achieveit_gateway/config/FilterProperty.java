package com.april.achieveit_gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Data
@Component
@ConfigurationProperties(prefix="local.filter")
public class FilterProperty
{
    private Set<String> allowPaths;
}