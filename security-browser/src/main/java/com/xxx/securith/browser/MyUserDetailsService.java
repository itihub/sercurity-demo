package com.xxx.securith.browser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: JiZhe
 * @CreateDate: 2018/8/26 16:19
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("login username : {}",username);
        // TODO: 2018/8/26 进行DB访问查询用户
        return new User(username, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }


}
