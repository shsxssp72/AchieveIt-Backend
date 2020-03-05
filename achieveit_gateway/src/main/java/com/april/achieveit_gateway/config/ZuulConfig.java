package com.april.achieveit_gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class ZuulConfig
{
    private static Logger logger=LoggerFactory.getLogger(ZuulConfig.class);

    @Bean
    public CorsFilter corsFilter()
    {
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration=new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**",
                                         configuration);
        logger.info("Filtered request, allowing cors");
        return new CorsFilter(source);
    }
}