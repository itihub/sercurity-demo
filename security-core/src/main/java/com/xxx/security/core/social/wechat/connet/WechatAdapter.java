package com.xxx.security.core.social.wechat.connet;

import com.xxx.security.core.social.wechat.api.Wechat;
import com.xxx.security.core.social.wechat.api.WechatUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @Description:微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 * @Author: JiZhe
 * @CreateDate: 2018/10/14 10:41
 */
public class WechatAdapter implements ApiAdapter<Wechat> {

    private String openId;

    public WechatAdapter() {
    }

    public WechatAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(Wechat api) {
        return true;
    }

    @Override
    public void setConnectionValues(Wechat api, ConnectionValues values) {
        WechatUserInfo userInfo = api.getUserInfo(openId);
        values.setProviderUserId(userInfo.getOpenid());
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getHeadimgurl());

    }

    @Override
    public UserProfile fetchUserProfile(Wechat api) {
        return null;
    }

    @Override
    public void updateStatus(Wechat api, String message) {
        //do nothing
    }
}
