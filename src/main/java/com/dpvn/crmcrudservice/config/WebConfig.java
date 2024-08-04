package com.dpvn.crmcrudservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**") // Apply CORS to all paths
        .allowedOrigins("*") // Allow this origin to make requests
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Allowed HTTP methods
        .allowedHeaders("*");
  }
}
