package com.vipdsilva.app.ws.config.swagger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.vipdsilva.app.ws.entities.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfigurations {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.vipdsilva.app.ws"))
        .paths(PathSelectors.any())
        .build()
        .globalOperationParameters(
            Arrays.asList(
                new ParameterBuilder()
                    .name("Authorization")
                    .description("Header para Token JWT")
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .required(false)
                    .build()
            ));
    }

/*     private ApiKey apiKey() { 
        return new ApiKey("JWT", "Authorization", "header"); 
    }

    private SecurityContext securityContext() { 
        return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
    } 
    
    private List<SecurityReference> defaultAuth() { 
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
        authorizationScopes[0] = authorizationScope; 
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
    } */

    private ApiInfo apiInfo() {
        return new ApiInfo(
          "Star Wars API",
          "Microsserviço para expor uma API sobre o mundo Star Wars.",
          "1.0",
          "",
          new Contact("Vinicius Pessoa da Silva", "", "vini.pessoa99@gmail.com"),
          "",
          "",
          Collections.emptyList());
    }

}
