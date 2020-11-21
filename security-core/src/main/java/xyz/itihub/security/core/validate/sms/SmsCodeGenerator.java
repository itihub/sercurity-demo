package xyz.itihub.security.core.validate.sms;

import xyz.itihub.security.core.properties.SecurityProperties;
import xyz.itihub.security.core.validate.ValidateCode;
import xyz.itihub.security.core.validate.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 短信验证码生成
 * @author: Administrator
 * @date: 2018/09/02 0002
 */
@Slf4j
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        //生成一个随机数
        String code = RandomStringUtils.randomNumeric(
                securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.code.getSms().getExpireIn());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
