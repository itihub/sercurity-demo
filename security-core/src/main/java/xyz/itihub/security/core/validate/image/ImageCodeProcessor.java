package xyz.itihub.security.core.validate.image;

import xyz.itihub.security.core.validate.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

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
