package com.xxx.security.core.validate.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 短信验证码发送默认实现
 * @author: Administrator
 * @date: 2018/09/02 0002
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.info("向 {} 发送验证码：{}", mobile, code);
    }
}
