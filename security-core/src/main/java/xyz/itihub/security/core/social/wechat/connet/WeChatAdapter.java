package xyz.itihub.security.core.social.wechat.connet;

import xyz.itihub.security.core.social.wechat.api.WeChat;
import xyz.itihub.security.core.social.wechat.api.WeChatUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @Description:微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 * @Author: JiZhe
 * @CreateDate: 2018/10/14 10:41
 */
public class WeChatAdapter implements ApiAdapter<WeChat> {

    private String openId;

    public WeChatAdapter() {
    }

    public WeChatAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(WeChat api) {
        return true;
    }

    @Override
    public void setConnectionValues(WeChat api, ConnectionValues values) {
        WeChatUserInfo userInfo = api.getUserInfo(openId);
        //第三方平台用户唯一标识
        values.setProviderUserId(userInfo.getOpenid());
        //用户显示昵称
        values.setDisplayName(userInfo.getNickname());
        //用户头像url
        values.setImageUrl(userInfo.getHeadimgurl());

    }

    @Override
    public UserProfile fetchUserProfile(WeChat api) {
        return null;
    }

    @Override
    public void updateStatus(WeChat api, String message) {
        //do nothing
    }
}
