package com.xxx.security.core.filter;

import com.xxx.security.core.enums.ValidateCodeExceptionEnum;
import com.xxx.security.core.exception.ValidateCodeException;
import com.xxx.security.core.validate.ImageCode;
import com.xxx.security.core.validate.ValidateCodeController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 继承OncePerRequestFilter 保证每次只会被调用一次
 *
 * @description: 校验图像验证码过滤器
 * @author: Administrator
 * @date: 2018/08/28 0028
 */
public class ValidateCodeFilter extends OncePerRequestFilter {


    /**
     * 失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 操作session工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        //判断是否需要校验的请求路径 并且 请求方式为post
        if (StringUtils.equals("", request.getRequestURI())
                &&
            StringUtils.equals(request.getMethod(), "post")) {

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
        ImageCode imageCode = (ImageCode) sessionStrategy.getAttribute(request,
                ValidateCodeController.SEEEION_KEY);

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        //判断是否为空
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(ValidateCodeExceptionEnum.VERIFICATION_CODE_NOT_EMPTY);
        }


    }
}
