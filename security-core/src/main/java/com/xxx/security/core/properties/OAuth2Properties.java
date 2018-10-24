package com.xxx.security.core.properties;

import lombok.Data;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2018/10/24 0024
 */
@Data
public class OAuth2Properties {

    /**
     * OAuth客户端配置  支持多组配置
     */
    private OAuth2ClientProperties[] clients = {};



}
