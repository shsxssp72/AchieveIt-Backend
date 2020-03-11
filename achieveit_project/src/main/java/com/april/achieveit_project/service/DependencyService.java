package com.april.achieveit_project.service;

import org.springframework.stereotype.Service;

@Service
public class DependencyService
{
    public String sendEmail()
    {
        return "Mails have been sent.";
    }
}
