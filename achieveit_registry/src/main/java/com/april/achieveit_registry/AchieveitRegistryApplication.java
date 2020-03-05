package com.april.achieveit_registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class AchieveitRegistryApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(AchieveitRegistryApplication.class,
                              args);
    }

}
