package com.april.achieveit_userinfo.service;

import com.april.achieveit_userinfo.mapper.UserInfoMapper;
import com.april.achieveit_userinfo_interface.entity.UserInfo;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationServiceTest
{
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserInfoMapper userInfoMapper;

    @BeforeAll
    public void setUp() throws Exception
    {
        System.out.println("setup");
        UserInfo userInfo=new UserInfo("SYKJ-20200201-0001",
                                       287991808128974852L,
                                       "mpan@hotmail.com",
                                       "466071c6546622410622052f2b285dd47e933021dfe1e9576a4d8bfa2abe85f20bdbe269a306e50d5bc671ea19bbc1e317abfa0539b2d148dd875ca6de0be645",
                                       "nrRPQXyABflAbUlJOlRGByAZHrjgxspLwrqyRmSnFCOuOcGaCjpGXcxAdeJfNgio",
                                       290104426675306497L);
        userInfoMapper.insert(userInfo);
    }
    //密码123123

    @Test
    void queryByUserId()
    {
        MatcherAssert.assertThat(authenticationService.queryByUserId("SYKJ-20200201-0001")
                                         .getUserName(),
                                 is("mpan@hotmail.com"));
    }

    @Test
    void verifyIdentity()
    {
        assertTrue(authenticationService.VerifyIdentity("SYKJ-20200201-0001",
                                                        "123123"));
    }

    @Test
    void login()
    {
        //System.out.println(authenticationService.Login("mpan@hotmail.com","123123"));
        String token=authenticationService.Login("mpan@hotmail.com",
                                                 "123123");
        MatcherAssert.assertThat(token.substring(0,
                                                 10),
                                 is("eyJhbGciOi"));
        System.out.println(authenticationService.RenewToken(token));
    }

    @Test
    void renewToken()
    {
    }

    @Transactional
    @Test
    void updateUserInfo()
    {
        authenticationService.UpdateUserInfo("SYKJ-20200201-0001",
                                             "123123",
                                             "321321");
        assertTrue(authenticationService.VerifyIdentity("SYKJ-20200201-0001",
                                                        "321321"));
    }

    @AfterAll
    public void tearDownAfterClass() throws Exception
    {
        userInfoMapper.deleteByPrimaryKey("SYKJ-20200201-0001");
    }
}