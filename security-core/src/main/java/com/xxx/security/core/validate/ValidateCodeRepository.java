package com.xxx.security.core.validate;

import com.xxx.security.core.enums.ValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletRequest;

/**
 * @Description: 验证码存储介质接口
 * @Author: JiZhe
 * @CreateDate: 2018/10/21 17:46
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     * @param request
     * @param validateCode 需要存放的验证码
     * @param validateCodeType  验证码类型
     */
    void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     * @param request
     * @param validateCodeType  验证码类型
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     * @param request
     * @param validateCodeType 验证码类型
     */
    void remove(ServletWebRequest request, ValidateCodeType validateCodeType);


}
