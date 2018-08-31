package com.xxx.security.core.validate;

import com.xxx.security.core.validate.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 验证码生成器
 * @author: Administrator
 * @date: 2018/08/31 0031
 */
public interface ValidateCodeGenerator {

    /**
     * 生成图形验证码
     * @param request
     * @return
     */
    ImageCode generateImageCode(ServletWebRequest request);
}
