package com.tsy.yebserver.config.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author Steven.T
 * @date 2021/11/18
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //包扫描配置
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tsy.yebserver.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("yeb接口文档")
                .description("yeb接口文档")
                .contact(new Contact("tsy", "localhost:8081/doc.html", "984337699@qq.com"))
                .version("1.0")
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        final ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        return List.of(apiKey);
    }

    private List<SecurityContext> securityContexts() {
        return List.of(SecurityContext.builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.regex("/*"))
                .build());
    }

    private List<SecurityReference> securityReferences() {
        return List.of(new SecurityReference("Authorization",
                List.of(new AuthorizationScope("global", "accessEverything"))
                        .toArray(new AuthorizationScope[0])));
    }
}
