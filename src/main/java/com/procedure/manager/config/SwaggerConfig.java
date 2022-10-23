package com.procedure.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
public class SwaggerConfig {

    @Value("${info.app.name}")
    private String projectName;

    @Value("${info.app.description}")
    private String projectDescription;

    @Value("${info.app.version}")
    private String projectVersion;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(basePackage(("com.procedure.manager")))
                .build()
                .apiInfo(getApiInfo())
                .useDefaultResponseMessages(false)
                .consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title(projectName)
                .description(projectDescription)
                .version(projectVersion)
                .build();
    }

}