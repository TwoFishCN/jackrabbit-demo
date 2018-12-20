package com.example.jackrabbit.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by HeWei on 2017/10/13.
 * .
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    @ConditionalOnMissingBean
    public Docket createRestApi() {
        Predicate<RequestHandler> selector = Predicates.or(RequestHandlerSelectors.basePackage("com.example.jackrabbit.controller"));

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(selector)
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Rabbit Demo RESTFul API")
                .version("v1.0")
                .build();
    }

}
