package com.xxx.security.core.validate;

import com.xxx.security.core.exception.ValidateCodeException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 验证码校验处理器模板接口
 * @author: Administrator
 * @date: 2018/09/02 0002
 */
public interface ValidateCodeProcessor {

    /**
     * session key pre前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void create(ServletWebRequest servletWebRequest) throws Exception;

    /**
     * 校验验证码逻辑
     * @param servletWebRequest
     * @throws ValidateCodeException 验证异常
     */
    void validate(ServletWebRequest servletWebRequest) throws ValidateCodeException;
}
