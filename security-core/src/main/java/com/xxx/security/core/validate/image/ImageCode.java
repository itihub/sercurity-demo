package com.xxx.security.core.validate.image;

import com.xxx.security.core.validate.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Description: 图像验证码model
 * @Author: JiZhe
 * @CreateDate: 2018/8/27 21:45
 */
@Data
public class ImageCode extends ValidateCode {

    /**
     * 图像验证码
     */
    private BufferedImage image;


    public ImageCode() {
    }

    @Deprecated
    public ImageCode(BufferedImage image, String code, LocalDateTime exprieTime) {
        super(code, exprieTime);
        this.image = image;

    }

    /**
     * 构造器
     *
     * @param image    验证图片
     * @param code     验证答案
     * @param expireIn 有效时间 单位/s
     */
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

}
