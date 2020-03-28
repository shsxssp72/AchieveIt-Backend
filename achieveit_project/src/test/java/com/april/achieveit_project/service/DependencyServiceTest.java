package com.april.achieveit_project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DependencyServiceTest
{

    @Autowired
    private DependencyService dependencyService;

    @Test
    void sendEmail()
    {
        System.out.println(dependencyService.sendEmail("",
                                                       "",
                                                       ""));
    }
}