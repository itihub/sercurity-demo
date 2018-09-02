package com.xxx.config;

import com.xxx.security.core.validate.ValidateCodeGenerator;
import com.xxx.security.core.validate.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 自定义验证码规则
 * @author: Administrator
 * @date: 2018/09/02 0002
 */
//@Component("imageCodeGenerator")
public class ImageCodeConfig implements ValidateCodeGenerator {


    @Override
    public ImageCode generate(ServletWebRequest request) {
        // FIXME: 2018/09/02 0002 书写自定义验证码规则
        return null;
    }
}
