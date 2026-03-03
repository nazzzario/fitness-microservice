package com.fitness.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI configuration for the User Service.
 * Provides API documentation through Swagger UI.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI documentation for the User Service.
     *
     * @return OpenAPI configuration with service information
     */
    @Bean
    public OpenAPI userServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .description("REST API for user management and authentication in the Fitness Microservice application")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Fitness Team")
                                .email("support@fitness.com")
                                .url("https://fitness.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
