package com.xxx.security.core.properties;

import lombok.Data;

/**
 * @description: OAuth客户端信息配置
 * @author: Administrator
 * @date: 2018/10/24 0024
 */
@Data
public class OAuth2ClientProperties {

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密码
     */
    private String clientSecret;

    /**
     * token有效期  默认0 发出令牌永不过期
     */
    private int accessTokenValiditySeconds;



}
