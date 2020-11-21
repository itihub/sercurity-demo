package xyz.itihub.securith.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.itihub.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义成功处理器
 * @author: Administrator
 * @date: 2018/08/27 0027
 */
@Slf4j
@Component
public class BaseAuthenticationSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request
            , HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        log.info("Login successful!");

        //判断响应类型
        switch (securityProperties.browser.getSingInResponseType()){
            case REDIRECT:
                //跳转（默认）
                super.onAuthenticationSuccess(request, response, authentication);
                break;
            case JSON:
                //设置响应格式
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                //将 Authentication 以json形式写回
                response.getWriter().write(objectMapper.writeValueAsString(authentication));
                break;
            default:
                log.info("not support response type : [{}]", securityProperties.browser.getSingInResponseType());
                //跳转（默认）
                super.onAuthenticationSuccess(request, response, authentication);
                break;
        }

    }
}
