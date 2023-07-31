package br.com.igbr.portfolioapi.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springApiPortifolioOpenApi(){
        return new OpenAPI().info(new Info()
                        .title("API-Portifolio")
                        .description("Projeto API-Portifolio")
                        .version("v0.0.1")
                        .license(new License()
                                .name("IGBR")
                                .url("https://github.com/Brumidori/API-portfolio"))
                        .contact(new Contact()
                                .name("Bruna Midori - linkedin")
                                .url("https://github.com/Brumidori")))
                .externalDocs(new ExternalDocumentation()
                        .description("Github")
                        .url("https://github.com/Brumidori/API-portfolio"));
    }

    private ApiResponse createApiResponse(String message){
        return new ApiResponse().description(message);
    }


}