package com.xxx.security.core.properties;

import lombok.Data;

/**
 * @description: 短信验证码 配置属性类
 * @author: Administrator
 * @date: 2018/08/30 0030
 */
@Data
public class SmsCodeProperties {

    /**
     * 长度
     */
    private int length = 6;

    /**
     * 过期时间
     */
    private int expireIn = 60;

    /**
     * 需要短信验证码的请求路径
     */
    private String url;


}
