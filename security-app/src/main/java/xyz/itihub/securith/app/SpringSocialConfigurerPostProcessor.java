package xyz.itihub.securith.app;

import xyz.itihub.security.core.social.XxxSpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @description: 实现BeanPostProcessor接口 所有spring bean在初始化都要处理一些流程
 * @author: Administrator
 * @date: 2018/10/24 0024
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    /**
     * 初始化之前
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //不做处理直接返回
        return bean;
    }

    /**
     * 初始化之后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        //判断是否有一个名字为 springSocialConfigurer 的bean
        if (StringUtils.equals(beanName, "springSocialConfigurer")){
            XxxSpringSocialConfigurer configurer = (XxxSpringSocialConfigurer) bean;
            //重新设置 signupUrl 的值
            configurer.signupUrl("/social/signUp");
            return configurer;
        }

        return bean;
    }
}
