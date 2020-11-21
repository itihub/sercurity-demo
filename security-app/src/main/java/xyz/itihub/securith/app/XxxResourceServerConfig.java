package xyz.itihub.securith.app;

import xyz.itihub.securith.app.social.OpenIdAuthenticationSecurityConfig;
import xyz.itihub.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import xyz.itihub.security.core.authorize.AuthorizeConfigManager;
import xyz.itihub.security.core.properties.SecurityConstants;
import xyz.itihub.security.core.social.SocialConfig;
import xyz.itihub.security.core.validate.ValidateCodeSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @description: 资源服务器
 * @see org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter
 * @author: Administrator
 * @date: 2018/10/19 0019
 */
@RequiredArgsConstructor
@Configuration
@EnableResourceServer
public class XxxResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**引入自定义成功处理器*/
    private final AuthenticationSuccessHandler baseAuthenticationSuccessHandle;

    /**引入自定义错误处理器*/
    private final AuthenticationFailureHandler baseAuthenticationFailureHandle;

    /** 短信验证安全配置*/
    private final SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /**
     * 社交配置 即第三方登录介入配置
     * 引入SpringSocialConfigurer
     * @see SocialConfig
     */
    private final SpringSocialConfigurer springSocialConfigurer;

    /**
     * 处理验证码过滤器配置
     */
    private final ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /**
     * openId换取认证token
     */
    private final OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    /**
     *权限配置管理器
     */
    private final AuthorizeConfigManager authorizeConfigManager;

    /**
     * 资源服务器配置
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                //默认登陆页
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                //登陆处理url
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                //配置自定义成功处理器
                .successHandler(baseAuthenticationSuccessHandle)
                //配置自定义失败处理器
                .failureHandler(baseAuthenticationFailureHandle);
        http
                //添加验证码安全配置
                // TODO: 2018/10/23 0023  无需用户名密码登录 图形验证码请注掉以下两行代码
                .apply(validateCodeSecurityConfig)
                .and()
                //添加短信安全配置
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                //openId换取认证token
                .apply(openIdAuthenticationSecurityConfig)
                .and()
                //添加 spring social配置
                .apply(springSocialConfigurer)
                .and()
                //关闭跨站请求防护
                .csrf()
                    .disable();

        authorizeConfigManager.config(http.authorizeRequests());
    }
}
