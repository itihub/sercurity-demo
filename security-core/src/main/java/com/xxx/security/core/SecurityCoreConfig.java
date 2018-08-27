package com.xxx.security.core;

import com.xxx.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;



/**
 * @EnableConfigurationProperties(SecurityProperties.class)
 * 让自定义配置读取器生效
 */

/**
 * @description: 配置类
 * @author: Administrator
 * @date: 2018/08/27 0027
 */

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
