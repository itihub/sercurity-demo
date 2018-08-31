package com.xxx.security.core.properties;

import lombok.Data;

/**
 * @description: 图形验证码 配置属性类
 * @author: Administrator
 * @date: 2018/08/30 0030
 */
@Data
public class ImageCodeProperties {

    /**
     * 宽度
     */
    private int width = 90;

    /**
     * 高度
     */
    private int height = 23;

    /**
     * 长度
     */
    private int length = 4;

    /**
     * 过期时间
     */
    private int expireIn = 60;

    /**
     * 需要图形验证码的请求路径
     */
    private String url;


}
