package xyz.itihub.demo.config.security;

import xyz.itihub.security.core.authentication.mobile.userdetails.MobileUser;
import xyz.itihub.security.core.authentication.mobile.userdetails.MobileUserDetails;
import xyz.itihub.security.core.authentication.mobile.userdetails.MobileUserDetailsService;
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
import xyz.itihub.demo.domain.UserEntity;
import xyz.itihub.demo.repository.UserRepository;

/**
 * @Description:登录业务逻辑处理
 * @Author: JiZhe
 * @CreateDate: 2018/8/26 16:19
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService, MobileUserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    /**
     * 表单登陆验证逻辑
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("request to login username : {}", username);

        //DB查询用户
        UserEntity user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User does not exist.");
        }

        String password = passwordEncoder.encode(user.getPassword());
        // 鉴权构造用户  OAth需要ROLE_USER才可访问API
        return new User(username, password
                , true, true, true, true
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("request to QQ login username : {}", userId);
        // TODO: 2018/8/26 使用userId（及第三方openid）进行DB UserConnection表访问查询用户信息
        String password = passwordEncoder.encode("123456");
        return new SocialUser(userId, password
                , true, true, true, true
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public MobileUserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        log.info("request to login mobile : {}", mobile);
        UserEntity user = userRepository.findByMobile(mobile);

        if (user == null){
            throw new UsernameNotFoundException("User does not exist.");
        }

        String password = passwordEncoder.encode(user.getPassword());
        //鉴权构造用户
        return new MobileUser(user.getUsername(), password
                , true, true, true, true
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
