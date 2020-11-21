package xyz.itihub.security.core.validate.sms;

/**
 * @description: 短信验证码发送接口
 * @author: Administrator
 * @date: 2018/09/02 0002
 */
public interface SmsCodeSender {

    /**
     * 发送短信验证码
     *
     * @param mobile 手机号码
     * @param code   验证码
     */
    void send(String mobile, String code);
}
