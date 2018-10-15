package com.xxx.security.core.social.wechat.connet;

import com.xxx.security.core.social.wechat.api.Wechat;
import com.xxx.security.core.social.wechat.api.WechatImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;

/**
 * @Description:
 * @Author: JiZhe
 * @CreateDate: 2018/10/14 10:43
 */
public class WechatServiceProvider extends AbstractOAuth2ServiceProvider<Wechat> {


    /**
     * 微信获取授权码的url
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public WechatServiceProvider(String appId, String appSecret) {
        super(new WechatOAuth2Template(appId, appSecret, URL_AUTHORIZE,URL_ACCESS_TOKEN));
    }

    @Override
    public Wechat getApi(String accessToken) {
        return new WechatImpl(accessToken);
    }
}
