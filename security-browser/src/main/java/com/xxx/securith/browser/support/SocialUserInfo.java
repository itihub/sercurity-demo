package com.xxx.securith.browser.support;

import lombok.Data;

/**
 * @Description:
 * @Author: JiZhe
 * @CreateDate: 2018/10/13 15:23
 */
@Data
public class SocialUserInfo {

    /**
     * 服务商唯一标识
     */
    private String providerId;

    /**
     * 服务商用户ID
     */
    private String providerUserId;

    /**
     * 开放ID
     */
    private String openId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户图片
     */
    private String headimg;
}
