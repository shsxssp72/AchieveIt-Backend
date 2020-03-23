package com.april.achieveit_userinfo.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.AssertTrue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthenticationServiceTest {

    @Autowired AuthenticationService authenticationService;
    @Test
    void queryByUserId() {
        Assert.assertThat(authenticationService.queryByUserId("SYKJ-20200201-0001").getUserName(),is("阿萨德"));
    }

    @Test
    void verifyIdentity() {
         Assert.assertTrue(authenticationService.VerifyIdentity("SYKJ-20200201-0001","321321"));
    }

    @Test
    void login() {
        Assert.assertThat(authenticationService.Login("阿萨德","321321").substring(0,10),is("eyJhbGciOi"));
    }

    @Test
    void renewToken() {
    }

    @Transactional
    @Test
    void updateUserInfo() {
        authenticationService.UpdateUserInfo("SYKJ-20200201-0001","321321","123123");
        Assert.assertTrue(authenticationService.VerifyIdentity("SYKJ-20200201-0001","123123"));
    }
}