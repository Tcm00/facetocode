package com.ming.facetocode.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger使用的配置文件
 * http://localhost:9090/swagger-ui.html
 * http://localhost:9090/doc.html
 * @author Tcm
 */
@Primary
@Configuration
@EnableSwagger2
@EnableKnife4j
//该注解是knife4j提供的增强注解,Ui提供了例如动态参数、参数过滤、接口排序等增强功能,如果你想使用这些增强功能就必须加该注解，否则可以不用加
public class SwaggerConfig {

    @Bean()
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    //基本信息的配置，信息会在api文档上显示
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("facetocode接口文档")
                .description("相关接口的文档")
                .termsOfServiceUrl("https://facetocode.xyz:9090/doc.html")
                .version("2.5")
                .build();
    }
}

