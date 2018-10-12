package com.xxx.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @description: 第三方登录接入api实现
 * @author: Administrator
 * @date: 2018/09/29 0029
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    /**
     * 获取QQ openid
     */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 获取用户信息
     */
    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    /**
     * QQ授权码
     */
    private String appId;

    /**
     * QQ用户唯一标识
     */
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 构造器
     * @param accessToken  令牌
     * @param appId QQ授权码
     */
    public  QQImpl(String accessToken, String appId){
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        //拼接请求路径
        String url = String.format(URL_GET_OPENID, accessToken);
        //调用父类通信模板发起请求
        String result = getRestTemplate().getForObject(url, String.class);

        this.openId = StringUtils.substringBetween(result, "\"openid\":", "}");
    }

    @Override
    public QQUserInfo getUserInfo(){

        String url = String.format(URL_GET_USER_INFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);


        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return objectMapper.readValue(result, QQUserInfo.class);
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }

    }
}
