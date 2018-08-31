package com.xxx.security.core.properties;

/**
 * @description: 验证码配置属性类
 * @author: Administrator
 * @date: 2018/08/30 0030
 */
public class ValidateCodeProperties {

    /**
     * 图形验证码 配置属性
     */
    private ImageCodeProperties image = new ImageCodeProperties();


    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
