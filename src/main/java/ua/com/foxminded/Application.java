package ua.com.foxminded;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import ua.com.foxminded.dao.DatebaseCreator;

@SpringBootApplication
@ServletComponentScan
@EnableJpaRepositories(basePackages = "ua.com.foxminded.dao")
@EntityScan(basePackages = "ua.com.foxminded.dao.entity")
public class Application extends SpringBootServletInitializer{
    
    public static void main(String[] args) {     
        DatebaseCreator datebaseCreator = new DatebaseCreator();
        datebaseCreator.createDatabase();
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Docket docket() {
      return new Docket(DocumentationType.OAS_30)
          .apiInfo(new ApiInfoBuilder()
              .title("University API")
              .description("A CRUD API")
              .version("0.0.1-SNAPSHOT")
              .license("Apache 2.0")
              .licenseUrl("http://springdoc.org")
              .build())
          .select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
          .build();
    }
}
