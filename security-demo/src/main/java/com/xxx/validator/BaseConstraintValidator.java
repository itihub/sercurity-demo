package com.xxx.validator;

import com.xxx.annotation.validator.BaseConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Package: com.xxx.validator
 * @Description: 自定义校验类
 * @Author: JiZhe
 * @CreateDate: 2018/8/25 17:04
 * @UpdateUser: Revised author
 * @UpdateDate: 2018/8/25 17:04
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */

/**
 * 自定义校验类
 * 实现 ConstraintValidator<A,T>接口  A 自定义的校验注解  T 被校验的类型
 * 实现方法
 * initialize  初始化方法
 * isValid(Object value, ConstraintValidatorContext context) 校验规则方法
 */

public class BaseConstraintValidator implements ConstraintValidator<BaseConstraint, Object> {


    /**
     * 初始化
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(BaseConstraint constraintAnnotation) {

    }

    /**
     * 校验规则
     *
     * @param value   校验值
     * @param context 校验上下文（包含注解的成员变量）
     * @return
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        //校验规则书写
        return false;
    }
}
