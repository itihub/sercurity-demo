package com.xxx.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @Description:登录业务逻辑处理
 * @Author: JiZhe
 * @CreateDate: 2018/8/26 16:19
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("request to login username : {}", username);
        // TODO: 2018/8/26 进行DB访问查询用户
        String password = passwordEncoder.encode("123456");
        return new User(username, password
                , true, true, true, true
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("request to QQ login username : {}", userId);
        // TODO: 2018/8/26 进行DB访问查询用户
        String password = passwordEncoder.encode("123456");
        return new SocialUser(userId, password
                , true, true, true, true
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
