package com.payconiq.stocks;

import java.util.Collections;
import org.javamoney.moneta.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  /**
   * Main Swagger API Bean
   *
   * @return Docket
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .directModelSubstitute(Money.class, String.class)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.payconiq.stocks"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  /**
   * Method describing api info.
   *
   * @return ApiInfo with parameters describing the application
   */
  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Stocks Rest microservice Payconiq",
        "Allowing create,get and update of Stocks",
        "1",
        "Terms of service",
        new Contact("Mohamed Abdelaziz", "www.payconiq.com", "m.aziz.selim@outlook.com"),
        "License of API",
        "#",
        Collections.emptyList());
  }
}
