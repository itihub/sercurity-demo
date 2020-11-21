package com.xxx.securith.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.security.core.properties.LoginType;
import com.xxx.security.core.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义认证成功处理器
 * @author: Administrator
 * @date: 2018/08/27 0027
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BaseAuthenticationSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    private final SecurityProperties securityProperties;

    private final ClientDetailsService clientDetailsService;

    private final AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request
            , HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        log.info("Login successful!");

        //获取请求头
        String header = request.getHeader("Authorization");
        //判断为空 或者 不是Basic开头
        if (header == null || !header.startsWith("Basic ")){
            throw new UnapprovedClientAuthenticationException("请求头中无client的信息");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        //根据clientId读取授权第三方信息
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null){
            throw new UnapprovedClientAuthenticationException(String.format("clientId：%s对应信息不存在", clientId));
        }else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)){
            throw new UnapprovedClientAuthenticationException(String.format("clientId：%s的clientSecret不匹配", clientId));
        }

        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        //构建访问令牌
        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        //设置响应格式
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        //将 accessToken 以json形式写回
        response.getWriter().write(objectMapper.writeValueAsString(accessToken));


    }

    /**
     * 提取和base64解析请求头
     * @param header
     * @param request
     * @return
     * @throws IOException
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {
        //截取前6位无用字符串 "Basic "
        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            //Base64解码
            decoded = Base64.decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        //定位":"下标
        int delim = token.indexOf(":");

        //没找到 ":"下标
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }

}
