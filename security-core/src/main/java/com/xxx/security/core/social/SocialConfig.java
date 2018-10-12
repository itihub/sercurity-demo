package com.xxx.security.core.social;

import com.xxx.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @description: Social 配置类
 * @author: Administrator
 * @date: 2018/10/08 0008
 */
@Component
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    /**
     * 注册数据源
     */
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        /**
         * 初次使用请执行此sql脚本
         * @see org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository.sql
         */
        // TODO: 2018/10/08 0008 数据库表名个性化前缀 释放此注释
//        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//        repository.setTablePrefix("");

        /**
         * @param dataSource 数据源
         * @param connectionFactoryLocator 连接工厂
         * @param textEncryptor 数据加解密方式
         */
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
    }

    /**
     * SpringSocialConfigurer 配置
     * @return
     */
    @Bean
    public SpringSocialConfigurer springSocialConfigurer() {
        String filterProcessesUrl = securityProperties.social.getQq().getFilterProcessesUrl();
        XxxSpringSocialConfigurer configurer = new XxxSpringSocialConfigurer(filterProcessesUrl);
        return configurer;
    }
}
