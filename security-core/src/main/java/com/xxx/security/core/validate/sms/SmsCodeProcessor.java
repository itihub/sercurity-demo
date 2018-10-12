package com.xxx.security.core.validate.sms;

import com.xxx.security.core.exception.ValidateCodeException;
import com.xxx.security.core.validate.AbstractValidateCodeProcessor;
import com.xxx.security.core.validate.ValidateCode;
import com.xxx.security.core.validate.sms.SmsCode;
import com.xxx.security.core.validate.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 短信验证码处理器
 * @author: Administrator
 * @date: 2018/09/02 0002
 */
@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * 短息验证码发送处理器
     *
     * @param servletWebRequest
     * @param ValidateCode
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest servletWebRequest, ValidateCode ValidateCode) throws Exception {
        //从请求中拿获取发送手机验证码的手机号码
        String mobile = ServletRequestUtils.getRequiredStringParameter(servletWebRequest.getRequest(), "mobile");
        smsCodeSender.send(mobile, ValidateCode.getCode());
    }
}
