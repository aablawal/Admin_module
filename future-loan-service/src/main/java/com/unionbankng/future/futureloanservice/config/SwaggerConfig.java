package com.unionbankng.future.futureloanservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.OAS_30)
          .select()
          .paths(PathSelectors.any())
          .apis(RequestHandlerSelectors.basePackage("com.unionbankng.future.futureloanservice.controllers"))
          .build().apiInfo(metaData());                                           
    }
    
     private ApiInfo metaData() {
        return new ApiInfoBuilder()
                 .title("Future Loan Service")
                .description("This service handles micro loan operations ")
                .version("0.0.1")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Rabiu Aliyu", "https://www.linkedin.com/in/rabiyu-aliyu-461569157/", "net.rabiualiyu@gmail.com"))
                .build();
    }

}