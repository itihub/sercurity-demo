package xyz.itihub.security.core.validate.sms;

import xyz.itihub.security.core.validate.ValidateCode;
import lombok.Data;

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
