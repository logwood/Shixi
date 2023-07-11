package org.example.shixi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4Config {
    @Bean
    public OpenAPI ApiMap()
    {
        return new OpenAPI()
                .info(new Info()
                        .title("基础版本Api库")
                        .description("系统管理，用户")
                        .version("1.2.0"));
    }
    @Bean
    public GroupedOpenApi groupedOpenApi(){
        return GroupedOpenApi.builder()
                .group("全部接口")
                .pathsToMatch("/**")
                .build();
    }
}
