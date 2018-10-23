package com.xxx.securith.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.security.core.properties.SecurityProperties;
import com.xxx.security.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义登出成功处理程序
 * @author: Administrator
 * @date: 2018/10/22 0022
 */
@Slf4j
public class XxxLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 登出URL
     */
    private String signOutUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 构造器
     * @param signOutUrl
     */
    public XxxLogoutSuccessHandler(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request
            , HttpServletResponse response
            , Authentication authentication) throws IOException, ServletException {

        log.info("登出成功");

        //判断 登出跳转url是够为空
        if (StringUtils.isBlank(signOutUrl)){
            //为 空 json形式返回
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        }else {
            //不为空 跳转
            response.sendRedirect(signOutUrl);
        }

    }
}
