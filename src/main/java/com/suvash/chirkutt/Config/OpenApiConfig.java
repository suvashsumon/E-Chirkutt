package com.suvash.chirkutt.Config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Chirkutt API")
                        .description("An API that provides the old school bangla chirkutt sending nostalgia. You will fill the old taste of opening a unnamed envelope.")
                        .version("1.0.0")
                        .license(new License().name("Suvash Kumar").url("http://github.com/suvashsumon")))
                .externalDocs(new ExternalDocumentation()
                        .description("Full Documentation")
                        .url("https://example.com"));
    }
}
