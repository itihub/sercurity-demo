package com.xxx.security.core.properties;

/**
 * @description: Social 配置属性
 * @author: Administrator
 * @date: 2018/10/08 0008
 */
public class SocialProperties {

    private QQProperties qq = new QQProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }
}
