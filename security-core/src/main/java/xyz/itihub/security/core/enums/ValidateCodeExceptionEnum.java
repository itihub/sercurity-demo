package xyz.itihub.security.core.enums;

/**
 * @description: 验证码错误枚举类
 * @author: Administrator
 * @date: 2018/08/28 0028
 */
public enum ValidateCodeExceptionEnum {

    VERIFICATION_CODE_NOT_EMPTY(300400, "验证码不能为空"),
    VERIFICATION_NOT_FOUND(300404, "验证码不存在"),
    VERIFICATION_INVALID(300502, "验证码已失效"),
    VERIFICATION_MISMATCH(300500, "验证码不匹配"),;


    private final Integer code;

    private final String msg;

    ValidateCodeExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
