package com.xxx.security.core.social.wechat.api;

/**
 * @Description:微信API
 * @Author: JiZhe
 * @CreateDate: 2018/10/13 16:03
 */
public interface Wechat {

    /**
     * 获取微信用户信息
     * @param openId
     * @return
     */
    WechatUserInfo getUserInfo(String openId);

}
