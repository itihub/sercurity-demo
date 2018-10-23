package com.xxx.security.core.properties;

import lombok.Data;

/**
 * @description: 浏览器配置项属性
 * @author: Administrator
 * @date: 2018/08/27 0027
 */
@Data
public class BrowserProperties {

    /**
     * 登陆跳转页
     */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    /**
     * 注册页
     */
    private String signUpUrl = "/signUp.html";

    /**
     * 登出后跳转页
     */
    private String signOutUrl ;

    /**
     * 错误处理机制：默认跳转/json返回
     * 默认跳转 即 SpringBoot 默认处理机制 BasicErrorController.class 进行处理
     */
    private LoginType loginType = LoginType.REDIRECT;

    /**
     * 记住我时间设置
     */
    private int rememberMeSeconds = 3600;

    /**
     * session管理
     */
    private SessionProperties session = new SessionProperties();


}
