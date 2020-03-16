package com.april.achieveit_userinfo.service;

import com.april.achieveit_common.utility.CryptoUtility;
import com.april.achieveit_common.utility.JWTUtility;
import com.april.achieveit_userinfo.mapper.UserInfoMapper;
import com.april.achieveit_userinfo_interface.entity.UserInfo;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService
{
    private Logger logger=LoggerFactory.getLogger(AuthenticationService.class);

    @Value("${local.server-identity}")
    String SERVER_IDENTITY;
    @Value("${local.shared-secret}")
    String SHARED_SECRET;
    @Value("${local.default-jwt-expire-seconds}")
    Integer DEFAULT_JWT_EXPIRE_SECONDS;
    @Value("${local.user-related-cache-valid-duration}")
    Integer USER_RELATED_CACHE_VALID_DURATION;
    @Value("${local.user-related-concurrent-wait-period}")
    Integer USER_RELATED_CONCURRENT_WAIT_PERIOD;

    @Autowired
    UserInfoMapper userInfoMapper;

    private UserInfo queryByUserId(String userId)
    {
        return userInfoMapper.selectByPrimaryKey(userId);
    }

    private UserInfo queryByUsername(String username)
    {
        return userInfoMapper.selectByUsername(username);
    }

    public boolean VerifyIdentity(String userId,String password)
    {
        UserInfo target=queryByUserId(userId);
        String challengePassphrase=CryptoUtility.HashPassword(password,
                                                              target.getUserName(),
                                                              target.getUserSalt());
        return target.getUserPassword()
                .equals(challengePassphrase);
    }

    @SneakyThrows
    public String Login(String username,String password)
    {
        String userId=queryByUsername(username).getUserId();

        if(password==null)
            throw new IllegalArgumentException("password should not be null");

        if(!VerifyIdentity(userId,
                           password))
            throw new IllegalArgumentException("Invalid login credential");

        return JWTUtility.SignJWT(String.valueOf(userId),
                                  DEFAULT_JWT_EXPIRE_SECONDS,
                                  SERVER_IDENTITY,
                                  SHARED_SECRET);

    }

    @SneakyThrows
    public String RenewToken(String token)
    {
        if(JWTUtility.VerifyJWT(token,
                                SHARED_SECRET))
        {
            String userId=JWTUtility.getSubjectFromJWT(token);
            logger.info("UserId: "+userId+" attempted to renew token.");

            return JWTUtility.SignJWT(userId,
                                      DEFAULT_JWT_EXPIRE_SECONDS,
                                      SERVER_IDENTITY,
                                      SHARED_SECRET);
        }
        throw new IllegalArgumentException("Renewing JWT Error");
    }

    /**
     * Will only update password
     */
    @SneakyThrows
    public void UpdateUserInfo(String userId,String originalPassword,String newPassword)
    {
        if(!VerifyIdentity(userId,
                           originalPassword))
            throw new AuthenticationException("Invalid login credential.");
        UserInfo target=userInfoMapper.selectByPrimaryKey(userId);
        if(StringUtils.isNotEmpty(newPassword)&&StringUtils.isNotBlank(newPassword))
        {
            target.setUserSalt(RandomStringUtils.randomAlphabetic(64));
            target.setUserPassword(CryptoUtility.HashPassword(newPassword,
                                                              target.getUserName(),
                                                              target.getUserSalt()));
        }
        userInfoMapper.updateByPrimaryKey(target);
    }

}
