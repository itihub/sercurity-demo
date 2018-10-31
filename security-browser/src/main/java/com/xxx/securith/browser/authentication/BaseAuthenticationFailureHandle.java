package com.xxx.securith.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.security.core.properties.LoginType;
import com.xxx.security.core.properties.SecurityProperties;
import com.xxx.security.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义失败处理器
 * @author: Administrator
 * @date: 2018/08/27 0027
 */
@Slf4j
@Component
public class BaseAuthenticationFailureHandle extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request
            , HttpServletResponse response
            , AuthenticationException exception)
            throws IOException, ServletException {

        log.info("登录失败");

        //判断响应类型
        if (LoginType.JSON.equals(securityProperties.browser.getSingInResponseType())) {

            //设置响应状态
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            //设置响应格式
            response.setContentType("application/json;charset=UTF-8");
            //将 Authentication 以json形式写回
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
        } else {
            //跳转（默任）
            super.onAuthenticationFailure(request, response, exception);
        }


    }
}
