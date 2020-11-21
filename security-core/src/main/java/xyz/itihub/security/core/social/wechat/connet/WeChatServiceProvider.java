package xyz.itihub.security.core.social.wechat.connet;

import xyz.itihub.security.core.social.wechat.api.WeChat;
import xyz.itihub.security.core.social.wechat.api.WeChatImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @Description:微信服务提供商
 * @Author: JiZhe
 * @CreateDate: 2018/10/14 10:43
 */
public class WeChatServiceProvider extends AbstractOAuth2ServiceProvider<WeChat> {


    /**
     * 微信获取授权码的url
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 构造器
     * @param appId 微信授权APPID
     * @param appSecret 微信授权APP密钥
     */
    public WeChatServiceProvider(String appId, String appSecret) {
        super(new WeChatOAuth2Template(appId, appSecret, URL_AUTHORIZE,URL_ACCESS_TOKEN));
    }

    @Override
    public WeChat getApi(String accessToken) {
        return new WeChatImpl(accessToken);
    }
}
