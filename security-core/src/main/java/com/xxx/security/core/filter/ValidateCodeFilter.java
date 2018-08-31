package com.xxx.security.core.filter;

import com.xxx.security.core.enums.ValidateCodeExceptionEnum;
import com.xxx.security.core.exception.ValidateCodeException;
import com.xxx.security.core.properties.SecurityProperties;
import com.xxx.security.core.validate.image.ImageCode;
import com.xxx.security.core.validate.image.ValidateCodeController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 继承OncePerRequestFilter 保证每次只会被调用一次
 *
 * @description: 校验图像验证码过滤器
 * @author: Administrator
 * @date: 2018/08/28 0028
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {


    /**
     * 失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 操作session工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 需要使用验证码的url集合
     */
    private Set<String> urls = new HashSet<>();

    /**
     * 引入应用级配置文件
     */
    @Autowired
    private SecurityProperties securityProperties;

    private AntPathMatcher pathMatcher;


    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        // TODO: 2018/08/31 0031 配置文件获取为null，需要处理
        //拿到配置的url
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(
                securityProperties.getCode().getImage().getUrl(), ",");

        //登录请求url
        urls.add("/authentication/form");

        if (configUrls == null) {
            return;
        }

        //添加到全局url集合中
        for (String configUrl : configUrls) {
            urls.add(configUrl);
        }

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        //是否进行验证标识
        boolean action = false;
        for (String url : urls) {
            String requestURI = request.getRequestURI();
            //判断是否有需要验证图形验证码的URI
            if (url.equals(requestURI)) {
                action = true;
            }
            // FIXME: 2018/08/31 0031 修复这块
//            if (pathMatcher.match(url, request.getRequestURI())) {
//                action = true;
//            }
        }

        //判断是否需要校验的请求路径
        if (action) {

            try {
                //进行验证码校验
                validate(new ServletWebRequest(request));
            }catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
        }

        filterChain.doFilter(request, response);

    }

    /**
     * 校验验证码
     * @param request
     * @throws ServletRequestBindingException
     * @throws ValidateCodeException    自定义校验异常
     */
    private void validate(ServletWebRequest request) throws ServletRequestBindingException, ValidateCodeException {

        //取出session
        ImageCode imageCodeSession = (ImageCode) sessionStrategy.getAttribute(request,
                ValidateCodeController.SEEEION_KEY);

        //获取用户输入的验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        //判断是否为空
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(ValidateCodeExceptionEnum.VERIFICATION_CODE_NOT_EMPTY);
        }

        //判断验证码是否为null
        if (imageCodeSession == null) {
            throw new ValidateCodeException(ValidateCodeExceptionEnum.VERIFICATION_NOT_FOUND);
        }

        //判断验证码是否过期
        if (imageCodeSession.isExpried()) {
            //移除过期session
            sessionStrategy.removeAttribute(request, ValidateCodeController.SEEEION_KEY);
            throw new ValidateCodeException(ValidateCodeExceptionEnum.VERIFICATION_INVALID);
        }

        //判断验证码是否匹配
        if (!StringUtils.equals(imageCodeSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(ValidateCodeExceptionEnum.VERIFICATION_MISMATCH);
        }
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
