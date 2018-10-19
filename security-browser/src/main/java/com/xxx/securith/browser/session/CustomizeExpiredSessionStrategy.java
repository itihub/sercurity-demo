package com.xxx.securith.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @description: 自定义session过期的会话策略
 * @author: Administrator
 * @date: 2018/10/19 0019
 */
public class CustomizeExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public CustomizeExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }


    /**
     * session失效处理
     * @param event session超时事件
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        //调用父类session失效处理
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
