package com.fitness.activityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@EnableMongoAuditing
@OpenAPIDefinition(
        info = @Info(
                title = "Activity Service API",
                version = "1.0",
                description = "API for managing fitness activities",
                contact = @Contact(name = "Fitness Team", email = "support@fitness.com")
        ),
        tags = {
                @Tag(name = "activity", description = "Operations related to activities")
        }
)
public class ActivityserviceApplication {

	static void main(String[] args) {
		SpringApplication.run(ActivityserviceApplication.class, args);
	}

}
