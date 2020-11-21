package xyz.itihub.security.core.exception;

import xyz.itihub.security.core.enums.ValidateCodeExceptionEnum;
import org.springframework.security.core.AuthenticationException;


/**
 * @description: 自定义验证码校验异常
 * @author: Administrator
 * @date: 2018/08/28 0028
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }

    public ValidateCodeException(ValidateCodeExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
    }


}
