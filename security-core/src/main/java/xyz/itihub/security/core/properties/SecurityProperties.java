package xyz.itihub.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 自定义配置读取器
 * @author: Administrator
 * @date: 2018/08/27 0027
 */

//读取配置文件以 xxx.security 的配置项

@ConfigurationProperties(prefix = "xyz.security")
public class SecurityProperties {

    /**
     * 浏览器配置属性类
     */
    public BrowserProperties browser = new BrowserProperties();

    /**
     * 验证码配置属性
     */
    public ValidateCodeProperties code = new ValidateCodeProperties();

    /**
     * 第三方Social配置属性
     */
    public SocialProperties social = new SocialProperties();


    /**
     * OAuth2认证客户端信息
     */
    public OAuth2Properties oauth2 = new OAuth2Properties();


    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
    }
}
