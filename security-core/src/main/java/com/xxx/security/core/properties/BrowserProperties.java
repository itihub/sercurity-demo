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
     * 登陆类型
     * 跳转/json返回
     */
    private LoginType loginType = LoginType.REDIRECT;


}
