package com.xxx.security.core.social.qq.connet;

import com.xxx.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2018/10/08 0008
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String appId, String appSecret){
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdopter());
    }
}
