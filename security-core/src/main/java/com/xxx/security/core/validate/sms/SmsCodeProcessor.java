package com.xxx.security.core.validate.sms;

import com.xxx.security.core.validate.AbstractValidateCodeProcessor;
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
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<SmsCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * 短息验证码发送处理器
     * @param servletWebRequest
     * @param smsCode
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest servletWebRequest, SmsCode smsCode) throws Exception {
        //从请求中拿获取发送手机验证码的手机号码
        String mobile = ServletRequestUtils.getRequiredStringParameter(servletWebRequest.getRequest(), "mobile");
        smsCodeSender.send(mobile, smsCode.getCode());
    }
}
