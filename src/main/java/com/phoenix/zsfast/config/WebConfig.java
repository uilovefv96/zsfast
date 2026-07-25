package com.phoenix.zsfast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor())
                .addPathPatterns("/admin.html", "/api/admin/**")   // 需要认证的路径
                .excludePathPatterns(
                        "/index.html",       // 公开首页
                        "/login.html",       // 登录页面
                        "/api/admin/login",            // 登录接口
                        "/favicon.ico",      // 图标
                        "/css/**",           // 静态样式
                        "/js/**",            // 静态脚本
                        "/webjars/**"        // 第三方库
                );
    }
}