package com.example.productservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AppConfig {

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
