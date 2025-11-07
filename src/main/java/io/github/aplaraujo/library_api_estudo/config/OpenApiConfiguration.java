package io.github.aplaraujo.library_api_estudo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Library API", version = "v1"))
public class OpenApiConfiguration {
}
