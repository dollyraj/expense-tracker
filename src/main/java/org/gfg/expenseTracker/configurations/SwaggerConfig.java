package org.gfg.expenseTracker.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("org.gfg.expenseTracker.controller"))
                .paths(PathSelectors.any()).build().apiInfo(getApiInfo());

    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title("Music-API Documentations")
                .description("Documentations for Music-App REST-API").version("0.0.1")
                .licenseUrl("http://localhost:8082/music-app")
                .contact(new Contact("Dolly.Raj", "dolly.raj.com", "dolly.raj@gmail.com"))
                .license("License of API").build();
    }

//swagger UI
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}

//http://localhost:8082/swagger-ui.html
//http://localhost:8082/v2/api-docs
//securityContextHolder is like a bucket