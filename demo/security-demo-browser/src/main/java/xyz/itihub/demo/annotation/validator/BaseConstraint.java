package xyz.itihub.demo.annotation.validator;

import xyz.itihub.demo.validator.BaseConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Package: com.xxx.annotation.validator
 * @Description: 自定义校验注解
 * @Author: JiZhe
 * @CreateDate: 2018/8/25 17:02
 * @UpdateUser: Revised author
 * @UpdateDate: 2018/8/25 17:02
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */

/**
 * @Target({ElementType.METHOD, ElementType.FIELD})  作用于 方法  成员变量
 * @Retention(RetentionPolicy.RUNTIME) 表示 是运行时注解
 * @Constraint(validatedBy = BaseConstraintValidator.class) 自定义校验器 指定校验类
 */

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BaseConstraintValidator.class)
public @interface BaseConstraint {

    String message() default "{this is BaseConstraint}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
