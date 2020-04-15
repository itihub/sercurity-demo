package com.xxx.security.core.enums;


import com.xxx.security.core.properties.SecurityConstants;

/**
 * @Description: 验证类型枚举
 * @Author: JiZhe
 * @CreateDate: 2018/9/24 19:20
 */
public enum ValidateCodeType {
    /**
     * 短信验证码
	 */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     * @return
     */
    public abstract String getParamNameOnValidate();
}
