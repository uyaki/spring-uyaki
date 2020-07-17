package com.uyaki.swagger292.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The type Swagger config.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value(value = "${swagger-host:localhost:8080}")
    private String host;

    /**
     * Api docket.
     *
     * @return the docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现,除了被@ApiIgnore指定的请求
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.uyaki.swagger292"))
                .paths(PathSelectors.any())
                .build()
                .host(host);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("uyaki-swagger292")
                .description("整合spring-boot-web + swagger292 ")
                .version("v1")
                .build();
    }
}
