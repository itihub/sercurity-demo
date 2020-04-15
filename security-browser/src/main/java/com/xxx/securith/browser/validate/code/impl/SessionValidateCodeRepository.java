package com.xxx.securith.browser.validate.code.impl;

import com.xxx.security.core.enums.ValidateCodeType;
import com.xxx.security.core.validate.ValidateCode;
import com.xxx.security.core.validate.ValidateCodeRepository;
import com.xxx.security.core.validate.image.ImageCodeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * @Description: 浏览器验证码存储实现
 * @Author: JiZhe
 * @CreateDate: 2018/10/21 17:57
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * session key pre前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 操作session工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * session存储验证码
     * @param request
     * @param validateCode 需要存放的验证码
     * @param validateCodeType  验证码类型
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType) {
        //重新构建ValidateCode 只需要存储 验证码 和 过期时间
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        sessionStrategy.setAttribute(request, getSessionKey(request)
                , code);
    }

    /**
     * session获取验证码
     * @param request
     * @param validateCodeType  验证码类型
     * @return
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request));
    }

    /**
     * session删除验证码
     * @param request
     * @param validateCodeType 验证码类型
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        sessionStrategy.removeAttribute(request, getSessionKey(request));
    }

    /**
     * 构建验证码放入session时的key
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }

    /**
     * 根据请求的url获取校验码的类型
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(ImageCodeProcessor.class.getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }
}
