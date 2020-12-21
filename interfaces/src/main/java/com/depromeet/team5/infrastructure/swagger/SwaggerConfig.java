package com.depromeet.team5.infrastructure.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("!prod")
@ConditionalOnWebApplication
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, this.createGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.POST, this.createGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, this.createGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, this.createGlobalResponseMessages())
                ;
    }

    private List<ResponseMessage> createGlobalResponseMessages() {
        return Stream.of(
                HttpStatus.BAD_REQUEST,
                HttpStatus.UNAUTHORIZED,
                HttpStatus.FORBIDDEN,
                HttpStatus.NOT_FOUND,
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.BAD_GATEWAY,
                HttpStatus.SERVICE_UNAVAILABLE
        )
                .map(this::createResponseMessage)
                .collect(Collectors.toList());
    }

    private ResponseMessage createResponseMessage(HttpStatus httpStatus) {
        return new ResponseMessageBuilder()
                .code(httpStatus.value())
                .message(httpStatus.getReasonPhrase())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("가슴 속 3천원 API")
                .description("가슴 속 3천원 API 입니다.")
                .build();
    }
}
