package com.xxx.securith.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义无效session会话策略
 * @author: Administrator
 * @date: 2018/10/19 0019
 */
public class CustomizeInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    public CustomizeInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        onSessionInvalid(request, response);
    }
}
