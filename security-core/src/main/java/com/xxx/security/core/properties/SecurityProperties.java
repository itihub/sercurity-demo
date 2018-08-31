package com.xxx.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 自定义配置读取器
 * @author: Administrator
 * @date: 2018/08/27 0027
 */

//读取配置文件以 xxx.security 的配置项

@ConfigurationProperties(prefix = "xxx.security")
public class SecurityProperties {

    /**
     * 配置属性类
     */
    public BrowserProperties browser = new BrowserProperties();

    /**
     * 验证码配置属性
     */
    public ValidateCodeProperties code = new ValidateCodeProperties();


    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }
}