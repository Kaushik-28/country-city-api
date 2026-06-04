package com.kaushikdas.countrycity.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI countryCityOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Country City API")
                        .description("""
                                REST API for retrieving country and city information.

                                This API supports:
                                - Listing countries
                                - Fetching country details
                                - Listing cities with pagination
                                - Fetching city details
                                - Filtering cities by country
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Kaushik Das")
                                .email("kaushik28.das@gmail.com"))
                        .license(new License()
                                .name("Internal Assignment API")));
    }
}