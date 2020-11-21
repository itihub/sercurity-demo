package xyz.itihub.securith.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.itihub.securith.app.social.AppSingUpUtils;
import xyz.itihub.security.core.support.SocialUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2018/10/24 0024
 */
@RequiredArgsConstructor
@RestController
public class AppSecurityController {

    private final ProviderSignInUtils providerSignInUtils;

    private final AppSingUpUtils appSingUpUtils;

    private final ObjectMapper objectMapper;

    @GetMapping("/social/signUp")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {

        SocialUserInfo userInfo = new SocialUserInfo();
        //使用工具类获取session中用户登录信息
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        //转换对象
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());

        //转从到redis
        appSingUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());

        return userInfo;
    }

//    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
//    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
//    public Object requireAuthentication(HttpServletRequest request
//            , HttpServletResponse response) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页")));
//        return null;
//    }

}
