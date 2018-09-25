package com.xxx.security.core.validate;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 验证码model
 * @author: Administrator
 * @date: 2018/09/02 0002
 */
@Data
public class ValidateCode {

    /**
     * 验证答案
     */
    private String code;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;


    public ValidateCode() {
    }

    @Deprecated
    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 构造器
     *
     * @param code     验证答案
     * @param expireIn 有效时间 单位/s
     */
    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    /**
     * 判断是否过期
     *
     * @return
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
