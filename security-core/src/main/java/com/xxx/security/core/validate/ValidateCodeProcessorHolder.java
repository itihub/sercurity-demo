package com.xxx.security.core.validate;

import com.xxx.security.core.enums.ValidateCodeType;
import com.xxx.security.core.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: 验证码处理支架
 * @author: Administrator
 * @date: 2018/09/25 0025
 */
@Component
public class ValidateCodeProcessorHolder {

    /**
     * 根据ValidateCodeProcessor接口依赖搜索实现此接口的bean
     */
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 根据验证类型查询验证处理器
     * @param type 验证类型
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    /**
     * 根据验证类型 查询验证处理器
     * @param type  验证类型
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        //拼接验证器实例beanName
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();

        //获取验证处理器的实例
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);

        if (processor == null) {
            throw new ValidateCodeException(String.format("%s Not Found", name));
        }
        return processor;
    }

}