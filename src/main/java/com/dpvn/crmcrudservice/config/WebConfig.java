package com.dpvn.crmcrudservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebConfig implements WebMvcConfigurer {

  private final OwnerContextInterceptor ownerContextInterceptor;

  public WebConfig(OwnerContextInterceptor ownerContextInterceptor) {
    this.ownerContextInterceptor = ownerContextInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(ownerContextInterceptor)
        .order(1)
        .addPathPatterns("/**") // áp dụng tất cả
        .excludePathPatterns("/health", "/actuator/**"); // bỏ qua healthcheck
  }
}
