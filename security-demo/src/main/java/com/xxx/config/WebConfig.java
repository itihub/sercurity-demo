package com.xxx.config;

import com.xxx.filter.TimeFilter;
import com.xxx.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:    web 配置
 * @Author: JiZhe
 * @CreateDate: 2018/8/25 21:13
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    /**
     * 过滤器注册
     * @return
     */
    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        //注册自定义过滤器
        TimeFilter timeFilter = new TimeFilter();
        registrationBean.setFilter(timeFilter);

        //设置过滤器起作用的路径
        List<String> urls = new ArrayList<>();
        urls.add("/*");

        registrationBean.setUrlPatterns(urls);

        return registrationBean;

    }


    @Autowired
    private TimeInterceptor timeInterceptor;

    /**
     * 注册自定义拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }


}
