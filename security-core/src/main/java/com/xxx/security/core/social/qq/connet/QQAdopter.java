package com.xxx.security.core.social.qq.connet;

import com.xxx.security.core.social.qq.api.QQ;
import com.xxx.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import java.io.IOException;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2018/10/08 0008
 */
public class QQAdopter implements ApiAdapter<QQ> {

    /**
     * 测试API是否可用
     * @param qq
     * @return
     */
    @Override
    public boolean test(QQ qq) {
        //不做测试处理 直接通过
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {
        //do noting
    }
}
