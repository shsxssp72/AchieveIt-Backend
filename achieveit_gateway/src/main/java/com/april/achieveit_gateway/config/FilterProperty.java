package com.april.achieveit_gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix="local.filter")
public class FilterProperty
{
    private List<String> allowPaths;
}