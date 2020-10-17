package com.beertech.bancobeer.relay.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${beertech.swagger.baseUrl}")
    private String baseUrl;

    @Value("${beertech.swagger.apiTitle}")
    private String apiTitle;

    @Value("${beertech.swagger.apiInfo}")
    private String apiInfo;

    @Bean
    public Docket microServicesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(baseUrl)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(metaData(apiInfo));
    }

    private ApiInfo metaData(String description) {
        final String projectVersion = "1.0";
        return new ApiInfoBuilder()
                .description(description)
                .version(projectVersion)
                .title(apiTitle)
                .contact(
                        new Contact("Becks Bank", "https://github.com/victorhrgc/beertech.bancobeer", "contato@becks,bank"))
                .license("Becks Bank © 2020")
                .build();
    }
}
