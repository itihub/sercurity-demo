package xyz.itihub.security.core.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 验证码生成器模板接口
 * @author: Administrator
 * @date: 2018/08/31 0031
 */
public interface ValidateCodeGenerator {

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}
