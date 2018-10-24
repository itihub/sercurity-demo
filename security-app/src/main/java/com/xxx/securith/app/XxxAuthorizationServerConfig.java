package com.xxx.securith.app;

import com.xxx.security.core.properties.OAuth2ClientProperties;
import com.xxx.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @description: OAuth认证服务器
 * @author: Administrator
 * @date: 2018/10/19 0019
 */
@Configuration
@EnableAuthorizationServer
public class XxxAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //使用redis存储令牌  注释一下行代码默认使用内存
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();

        //判断是否配置oauth client
        if (ArrayUtils.isNotEmpty(securityProperties.oauth2.getClients())){
            //遍历配置属性
            for (OAuth2ClientProperties config : securityProperties.oauth2.getClients()) {

                builder.withClient(config.getClientId())
                        .secret(config.getClientSecret())
                        //发出令牌的有效期 单位秒
                        .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
                        //支持的认证模式
                        .authorizedGrantTypes("refresh_token", "password")
                        //发出token的权限
                        .scopes("all", "read", "write");
            }

        }

    }
}
