package com.xxx.securith.app.social.openid;

import com.xxx.security.core.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: OpenId认证过滤器
 * @author: Administrator
 * @date: 2018/10/23 0023
 */
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID;
    private String providerIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDERID;
    private boolean postOnly = true;

    /**
     * 构造器
     * 会拦截 请求方式为post 请求路径为 /authentication/openid 的请求
     */
    public OpenIdAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        //判断当前请求方式是否符合规则
        if (postOnly && !request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported" + request.getMethod());
        }

        //获取参数
        String openId = obtainOpenId(request);
        String providerId = obtainProviderId(request);

        if (openId == null){
            openId = "";
        }
        if (providerId == null){
            providerId = "";
        }

        //参数去空格
        openId = openId.trim();
        providerId = providerId.trim();

        //构建自定义未认证token
        OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openId, providerId);

        //设置请求详情
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);

    }

    /**
     * 请求详情设置到OpenIdAuthenticationToken中
     * @param request
     * @param authRequest
     */
    private void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 获取服务商id
     * @param request
     * @return
     */
    private String obtainProviderId(HttpServletRequest request) {
        return request.getParameter(providerIdParameter);
    }

    /**
     * 获取openId
     * @param request
     * @return
     */
    private String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(openIdParameter);
    }

    public boolean isPostOnly() {
        return postOnly;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
