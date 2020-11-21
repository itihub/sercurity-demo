package xyz.itihub.security.core.social.qq.connet;

import xyz.itihub.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @description: QQ连接工厂
 * @author: Administrator
 * @date: 2018/10/08 0008
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * 构造QQ连接工厂
     * @param providerId 提供商唯一ID
     * @param appId
     * @param appSecret
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret){
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdopter());
    }
}
