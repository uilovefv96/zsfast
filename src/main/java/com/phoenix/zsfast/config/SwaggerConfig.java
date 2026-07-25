package com.phoenix.zsfast.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("全球物流轨迹查询系统 API")
                        .version("1.0")
                        .description("物流订单查询、轨迹追踪及后台数据录入接口文档")
                        .contact(new Contact()
                                .name("Phoenix 开发团队")
                                .email("support@phoenix.com")));
    }
}