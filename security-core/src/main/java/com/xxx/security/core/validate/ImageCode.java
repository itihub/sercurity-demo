package com.xxx.security.core.validate;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Description: 图像验证码
 * @Author: JiZhe
 * @CreateDate: 2018/8/27 21:45
 */
@Data
public class ImageCode {

    /**
     *图像验证码
     */
    private BufferedImage image;

    /**
     * 验证答案
     */
    private String code;

    /**
     * 过期时间
     */
    private LocalDateTime exprieTime;


    public ImageCode() {
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime exprieTime) {
        this.image = image;
        this.code = code;
        this.exprieTime = exprieTime;
    }

    /**
     * 构造器
     * @param image 验证图片
     * @param code  验证答案
     * @param exprieIn  有效时间 单位/s
     */
    public ImageCode(BufferedImage image, String code, int exprieIn) {
        this.image = image;
        this.code = code;
        this.exprieTime = LocalDateTime.now().plusSeconds(exprieIn);
    }
}
