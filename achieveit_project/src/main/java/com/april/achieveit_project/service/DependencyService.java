package com.april.achieveit_project.service;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_project.client.DependencyServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DependencyService
{
    @Autowired
    private DependencyServiceClient dependencyServiceClient;

    public String sendEmail(String topic,String receiver,String content)
    {
        Map<String,String> params=Map.of("topic",
                                         topic,
                                         "receiver",
                                         receiver,
                                         "content",
                                         content);
        ResponseContent response=dependencyServiceClient.EmailService(params);

        return response.getMessage();
    }
}
