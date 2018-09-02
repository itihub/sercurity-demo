package com.xxx.security.core.validate.sms;

import com.xxx.security.core.validate.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Description: 短信验证码model
 * @Author: JiZhe
 * @CreateDate: 2018/8/27 21:45
 */
@Data
public class SmsCode extends ValidateCode {


    public SmsCode(String code, int expireIn) {
        super(code, expireIn);
    }
}
