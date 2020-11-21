package xyz.itihub.securith.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.itihub.security.core.properties.SecurityProperties;
import xyz.itihub.security.core.support.SimpleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义认证失败处理器
 * @author: Administrator
 * @date: 2018/08/27 0027
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BaseAuthenticationFailureHandle extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    private final SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request
            , HttpServletResponse response
            , AuthenticationException exception)
            throws IOException, ServletException {

        log.info("Login failed!");


        //设置响应状态
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        //设置响应格式
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        //将 Authentication 以json形式写回
        response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));


    }
}
