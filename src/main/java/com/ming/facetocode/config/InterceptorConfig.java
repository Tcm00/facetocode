package com.ming.facetocode.config;

import com.ming.facetocode.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Tcm
 *
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                //不放行的接口;通常所有不放行
                .addPathPatterns("/**")
                //放行，
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/register")
                .excludePathPatterns("/admin/sendEmail")
//                .excludePathPatterns("/admin/explain")
                .excludePathPatterns("/getSinaData")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/getVideo/**")
                //swagger请求
                .excludePathPatterns("/v2/api-docs")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger**/**")
                .excludePathPatterns("/doc.html/**")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/face/getImg2/**")
                .excludePathPatterns("/title/**");
    }

}
