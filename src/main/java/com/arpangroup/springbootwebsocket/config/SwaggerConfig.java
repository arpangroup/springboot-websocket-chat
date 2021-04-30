package com.arpangroup.springbootwebsocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
//@Profile({"dev","test", "prod"})
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.arpangroup.springbootwebsocket.controller"))
                //.paths(regex("/api.*"))//.paths(PathSelectors.any())
                .build();
                //.apiInfo(metaInfo());
    }


    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("Rupey APIS for MobileApp Development")
                .description("Api consist of: <br> https://github.com/arpangroup")
                .version("1.0")
                .termsOfServiceUrl("Terms of Service")
                .contact(new Contact("Arpan Jana", "www.pureeats.in", "pureeats.help.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licesen.html")
                .build();
    }

}
