package com.malick.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Value("${spring.data.rest.base-path}")
    private String basePath;

    @Value("${origin-domain-allowed}")
    private String[] originDomainAllowed;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(this.basePath + "/**").allowedOrigins(this.originDomainAllowed);
    }
}
