package xyz.itihub.securith.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

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

    /**
     * 构造器
     * @param invalidSessionUrl
     */
    public CustomizeInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 调用父类session失效处理
        onSessionInvalid(request, response);
    }
}
