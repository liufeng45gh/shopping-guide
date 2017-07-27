package com.lucifer.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lijc on 15/8/27.
 */
@Configuration
@EnableSwagger
@EnableAutoConfiguration
public class SwaggerConfig {

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;



    @Bean
    // Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(
                apiInfo()).includePatterns(".*?");
    }
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "用户系统 API",
                "API for 用户系统",
                null,
                null,
                null,
                null);
        return apiInfo;
    }
}
