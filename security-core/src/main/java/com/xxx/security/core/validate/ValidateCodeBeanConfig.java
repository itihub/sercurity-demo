package com.xxx.security.core.validate;

import com.xxx.security.core.properties.SecurityProperties;
import com.xxx.security.core.validate.ValidateCodeGenerator;
import com.xxx.security.core.validate.image.ImageCodeGenerator;
import com.xxx.security.core.validate.sms.DefaultSmsCodeSender;
import com.xxx.security.core.validate.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 验证码配置bean
 * @author: Administrator
 * @date: 2018/08/31 0031
 */
@Configuration
public class ValidateCodeBeanConfig {

    /**
     * 引入应用级配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @see @ConditionalOnMissingBean(name = "imageCodeGenerator")
     *      此注解在 Spring 启动时，先去 寻找 imageCodeGenerator 的Bean,如果存在就不执行
     *      imageCodeGenerator() 方法,存在则执行
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    /**
     * @see @ConditionalOnMissingBean(SmsCodeSender.class)
     *      此注解首先寻找是够有实现 SmsCodeSender.class接口，如果有则不执行使用实现接口，
     *      如果没有使用默认
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public DefaultSmsCodeSender smsCodeGenerator() {
        return new DefaultSmsCodeSender();
    }
}