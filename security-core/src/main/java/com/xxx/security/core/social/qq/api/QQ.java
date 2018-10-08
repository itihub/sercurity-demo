package com.xxx.security.core.social.qq.api;

import java.io.IOException;

/**
 * @description: 第三方登录接入api
 * @author: Administrator
 * @date: 2018/09/29 0029
 */
public interface QQ {

    /**
     * 获取QQ用户信息
     * @return
     */
    QQUserInfo getUserInfo();
}
