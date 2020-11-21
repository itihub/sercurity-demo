package xyz.itihub.security.core.validate;

import xyz.itihub.security.core.enums.ValidateCodeType;
import xyz.itihub.security.core.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: 验证码生成器支架
 * @author: Administrator
 * @date: 2018/09/25 0025
 */
@Component
public class ValidateCodeGeneratorHolder {

    /**
     * 依赖搜索
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现
     * map key 为bean的名称
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 根据请验证类型查询验证码生成器实例
     * @param type 验证类型枚举
     * @return
     */
    public ValidateCodeGenerator findValidateCodeGenerator(ValidateCodeType type) {
        return findValidateCodeGenerator(type.toString().toLowerCase());
    }

    /**
     * 根据验证类型 查询验证码生成器实例
     * @param type 验证类型
     * @return
     */
    public ValidateCodeGenerator findValidateCodeGenerator(String type) {
        String suffix = StringUtils.substringAfter(ValidateCodeGenerator.class.getSimpleName(), "Validate");
        //拼接生成器beanName
        String name = type.toLowerCase() + suffix;
        //通过依赖搜索查询实例beanName
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(name);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException(String.format("%sCodeGenerator Not Found", name));
        }
        return validateCodeGenerator;
    }

}
