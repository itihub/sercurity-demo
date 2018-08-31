package com.xxx.security.core.properties;

import lombok.Data;

/**
 * @description: 配置项属性
 * @author: Administrator
 * @date: 2018/08/27 0027
 */
@Data
public class BrowserProperties {

    /**
     * 登陆跳转页
     */
    private String loginPage = "/login.html";

    /**
     * 错误处理机制：默认跳转/json返回
     * 默认跳转 即 SpringBoot 默认处理机制 BasicErrorController.class 进行处理
     */
    private LoginType loginType = LoginType.REDIRECT;


}
