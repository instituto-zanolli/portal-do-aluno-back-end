package com.zanolli.backend.shared.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger {

    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("com.zanolli.backend.modules")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    OpenAPI custom() {
        return new OpenAPI()
                .info(new Info()
                        .title("Zanolli")
                        .description("teste")
                        .version("1.0.0")
                );
    }

}
