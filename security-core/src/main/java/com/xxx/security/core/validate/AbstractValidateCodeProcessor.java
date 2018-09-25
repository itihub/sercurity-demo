package com.xxx.security.core.validate;

import com.xxx.security.core.enums.ValidateCodeExceptionEnum;
import com.xxx.security.core.enums.ValidateCodeType;
import com.xxx.security.core.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 验证处理器抽象实现
 * @author: Administrator
 * @date: 2018/09/02 0002
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {


    /**
     * 操作session工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    @Autowired
    private ValidateCodeGeneratorHolder validateCodeGeneratorHolder;

    /**
     * 生成验证码
     * @param servletWebRequest
     * @throws Exception
     */
    @Override
    public void create(ServletWebRequest servletWebRequest) throws Exception {
        //生成验证码
        C validateCode = generate(servletWebRequest);
        //保存验证码
        save(servletWebRequest, validateCode);
        //发送验证码
        send(servletWebRequest, validateCode);
    }

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorHolder.findValidateCodeGenerator(type);
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        sessionStrategy.setAttribute(request
                , getSessionKey(request)
                , validateCode);
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
     * 抽象方法 发送校验码，由子类实现
     * @param servletWebRequest
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest servletWebRequest, C validateCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }


    /**
     * 校验验证码
     * @param request
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);

        //取出session
        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;
        try {
            //取出session相关的值
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        //判断是否为空
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(ValidateCodeExceptionEnum.VERIFICATION_CODE_NOT_EMPTY);
        }

        //判断验证码是否为null
        if (codeInSession == null) {
            throw new ValidateCodeException(ValidateCodeExceptionEnum.VERIFICATION_NOT_FOUND);
        }
        //判断验证码是否过期
        if (codeInSession.isExpired()) {
            //移除过期session
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(ValidateCodeExceptionEnum.VERIFICATION_INVALID);
        }
        //判断验证码是否匹配
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(ValidateCodeExceptionEnum.VERIFICATION_MISMATCH);
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }
}
