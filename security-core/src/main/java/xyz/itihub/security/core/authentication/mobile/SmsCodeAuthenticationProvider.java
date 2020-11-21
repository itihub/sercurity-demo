package xyz.itihub.security.core.authentication.mobile;

import xyz.itihub.security.core.authentication.mobile.userdetails.MobileUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Description:短信登陆认证类
 * @Author: JiZhe
 * @CreateDate: 2018/9/24 17:41
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private MobileUserDetailsService userDetailsService;

    /**
     * 短信验证码身份认证逻辑
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 将认证转换成SmsCodeAuthenticationToken
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        // 获取用户登陆信息(根据手机号码查询用户信息)
        UserDetails user = userDetailsService.loadUserByMobile((String) authenticationToken.getPrincipal());

        if (user == null){
            throw new InternalAuthenticationServiceException("无法读取用户信息");
        }

        // 构造已认证token
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //判断认证类型是否为SmsCodeAuthenticationToken
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public MobileUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(MobileUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
