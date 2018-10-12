package com.xxx.security.core.social.qq.connet;

import com.xxx.security.core.social.qq.api.QQ;
import com.xxx.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @description: 第三方API 与 Spring social标准API进行转换
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

    /**
     * 适配连接
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        //设置用户昵称
        values.setDisplayName(userInfo.getNickname());
        //设置用户头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        //设置个人主页（类似微博个人主页，QQ没有个人主页）
        values.setProfileUrl(null);
        //设置用户在服务商的唯一标识
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    /**
     * 更新状态  （QQ不支持）
     * @param qq
     * @param s
     */
    @Override
    public void updateStatus(QQ qq, String s) {
        //do noting
    }
}
