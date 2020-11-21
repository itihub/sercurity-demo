package xyz.itihub.security.core.social.qq.connet;

import xyz.itihub.security.core.social.qq.api.QQ;
import xyz.itihub.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @description: QQ服务提供商
 * @author: Administrator
 * @date: 2018/10/08 0008
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    /**
     * 获取QQ授权码地址
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 获取访问QQ访问令牌
     */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId, String appSecret){
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
