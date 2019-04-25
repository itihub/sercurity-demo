package com.xxx.securith.app;

import com.xxx.securith.app.jwt.XxxJwtTokenEnhancer;
import com.xxx.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

/**
 * @description: 令牌存储配置
 * @author: Administrator
 * @date: 2018/10/24 0024
 */
@Component
public class TokenStoreConfig {

    /**
     * redis连接工厂
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 限定检查 如果有配置xxx.security.oauth2.storeType=redis  此配置文件生效
     * @return
     */
//    @Bean
//    @ConditionalOnProperty(prefix = "xxx.security.oauth2", name = "storeType", havingValue = "redis")
//    public TokenStore redisTokenStore(){
//        return new RedisTokenStore(redisConnectionFactory);
//    }

    /**
     * jwt替换默认生成token策略
     * 限定检查 如果有配置xxx.security.oauth2.storeType=jwt 或者无此配置 此配置文件生效
     */
    @Configuration
    @ConditionalOnProperty(prefix = "xxx.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenConfig{

        @Autowired
        private SecurityProperties securityProperties;


        /**
         * token存储处理
         * @return
                 */
        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * token生成处理
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            //密签 key
            accessTokenConverter.setSigningKey(securityProperties.oauth2.getSigningKey());
            return accessTokenConverter;
        }

        /**
         * 支持扩展
         * @return
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer(){
            return new XxxJwtTokenEnhancer();
        }

    }

}
