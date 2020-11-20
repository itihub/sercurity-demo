package xyz.itihub.demo.config.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * @Description: 第三方登陆 用户自动注册实现案例
 * @Author: JiZhe
 * @CreateDate: 2018/10/13 15:43
 */
//@Component
public class DemoConnectionSingUp implements ConnectionSignUp {

    /**
     * 若实现ConnectionSignUp接口
     * 使用第三方新用户登陆 自动注册流程实现
     */

    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户并返回用户唯一标识
        // TODO: 2018/10/13 注册业务逻辑
        return connection.getDisplayName();
    }
}
