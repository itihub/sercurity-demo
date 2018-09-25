package com.xxx.security.core.validate.image;

import com.xxx.security.core.enums.ValidateCodeExceptionEnum;
import com.xxx.security.core.exception.ValidateCodeException;
import com.xxx.security.core.validate.AbstractValidateCodeProcessor;
import com.xxx.security.core.validate.ValidateCodeController;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 图形验证码处理器
 * @author: Administrator
 * @date: 2018/09/02 0002
 */
@Component("imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    /**
     * 图形验证码发送处理器
     *
     * @param servletWebRequest
     * @param imageCode
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest servletWebRequest, ImageCode imageCode) throws Exception {
        //写回到response
        ImageIO.write(imageCode.getImage(), "JPEG"
                , servletWebRequest.getResponse().getOutputStream());

    }
}
