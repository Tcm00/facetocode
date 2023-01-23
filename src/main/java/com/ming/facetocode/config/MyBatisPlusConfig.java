package com.ming.facetocode.config;


import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author 小明
 * @date 2021/10/31
 * @description
 */
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {
//        乐观锁
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor(){
        return new OptimisticLockerInnerInterceptor();
    }
    //    设置 dev test 环境开启，保证我们的效率
    @Bean
    @Profile({"dev","test"})
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        PerformanceMonitorInterceptor performanceMonitorInterceptor = new PerformanceMonitorInterceptor();
        return performanceMonitorInterceptor;
    }

}