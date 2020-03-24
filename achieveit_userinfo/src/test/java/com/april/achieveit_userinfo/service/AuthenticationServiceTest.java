package com.april.achieveit_userinfo.service;

import com.april.achieveit_userinfo.mapper.UserInfoMapper;
import com.april.achieveit_userinfo_interface.entity.UserInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationServiceTest {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserInfoMapper userInfoMapper;
    @BeforeAll
    public void setUp() throws Exception{
        System.out.println("setup");
        UserInfo userInfo = new UserInfo("SYKJ-20200201-0001",287991808128974852L,"mpan@hotmail.com","466071c6546622410622052f2b285dd47e933021dfe1e9576a4d8bfa2abe85f20bdbe269a306e50d5bc671ea19bbc1e317abfa0539b2d148dd875ca6de0be645","nrRPQXyABflAbUlJOlRGByAZHrjgxspLwrqyRmSnFCOuOcGaCjpGXcxAdeJfNgio",290104426675306497L);
        userInfoMapper.insert(userInfo);
    }
    //密码123123

    @Test
    void queryByUserId() {
        Assert.assertThat(authenticationService.queryByUserId("SYKJ-20200201-0001").getUserName(),is("mpan@hotmail.com"));
    }

    @Test
    void verifyIdentity() {
         Assert.assertTrue(authenticationService.VerifyIdentity("SYKJ-20200201-0001","123123"));
    }

    @Test
    void login() {
        //System.out.println(authenticationService.Login("mpan@hotmail.com","123123"));
        Assert.assertThat(authenticationService.Login("mpan@hotmail.com","123123").substring(0,10),is("eyJhbGciOi"));
    }

    @Test
    void renewToken() {
    }

    @Transactional
    @Test
    void updateUserInfo() {
        authenticationService.UpdateUserInfo("SYKJ-20200201-0001","123123","321321");
       Assert.assertTrue(authenticationService.VerifyIdentity("SYKJ-20200201-0001","321321"));
    }

    @AfterAll
    public  void tearDownAfterClass() throws Exception{
    userInfoMapper.deleteByPrimaryKey("SYKJ-20200201-0001");
    }


}