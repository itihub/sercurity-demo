package com.xxx.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description: Swagger配置
 * @Author: JiZhe
 * @CreateDate: 2018/8/26 13:05
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //显示指定包
                .apis(RequestHandlerSelectors.basePackage("com.xxx"))
                //默认显示全部
//                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();

        return docket;
    }

    /**
     * API信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置文档的标题
                .title("Swagger2 接口文档示例")
                //设置文档的描述->1.Overview
                .description("this is api by JiZhe")
                //设置文档的版本信息-> 1.1 Version information
                .version("1.0")
                //设置文档的联系方式->1.2 Contact information
                .contact(new Contact("JiZhe", "xxx", "jizhe@live.cn"))
                //设置文档的License信息->1.3 License information
                .termsOfServiceUrl("www.baidu.com")
                .build();
    }
}
