package com.april.achieveit_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class AchieveitGatewayApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(AchieveitGatewayApplication.class,
                              args);
    }

}
