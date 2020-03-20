package com.xxx.security.core.filter;

import com.xxx.security.core.enums.ValidateCodeType;
import com.xxx.security.core.exception.ValidateCodeException;
import com.xxx.security.core.properties.SecurityConstants;
import com.xxx.security.core.properties.SecurityProperties;
import com.xxx.security.core.validate.ValidateCodeProcessorHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 继承OncePerRequestFilter 保证每次只会被调用一次
 *
 * @description: 通用验证过滤器
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
     * 存放需要使用验证码的url集合
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();


    /**
     * 引入应用级配置文件
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 验证处理支架   主要根据验证类型查询验证处理器实例
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;


    /**
     * 初始化加载拦截配置
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        // 加入表单登陆请求路径
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        // 加入验证码校验请求路径
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        // 加入短信登陆请求路径
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        // 加入短信校验登陆请求路径
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }

    /**
     * 数组参数存入Map中
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        //根据请求路径获取验证类型
        ValidateCodeType validateCodeType = getValidateCodeType(request);

        //判断是否有匹配的验证类型
        if (validateCodeType != null){
            try {
                //根据验证类型查询验证实例并进行验证
                validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeType)
                        .validate(new ServletWebRequest(request, response));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }

        }

        //调用下一个过滤器
        filterChain.doFilter(request, response);

    }


    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            //取出map的key
            Set<String> urls = urlMap.keySet();
            //遍历key
            for (String url : urls) {
                //查询是否有匹配的请求路径
                if (pathMatcher.match(url, request.getRequestURI())) {
                    //获取验证类型
                    result = urlMap.get(url);
                }
            }
        }
        return result;
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

    public ValidateCodeProcessorHolder getValidateCodeProcessorHolder() {
        return validateCodeProcessorHolder;
    }

    public void setValidateCodeProcessorHolder(ValidateCodeProcessorHolder validateCodeProcessorHolder) {
        this.validateCodeProcessorHolder = validateCodeProcessorHolder;
    }
}
