package com.dpvn.crmcrudservice.client;

import com.dpvn.sharedcore.async.UserContext;
import com.dpvn.sharedcore.config.CustomFeignLogger;
import com.dpvn.sharedcore.domain.constant.Globals;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {
  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      if (UserContext.getCurrentSeller() != null) {
        requestTemplate.header(
            Globals.Headers.SELLER_HEADER, UserContext.getCurrentSeller().toString());
      }
      if (UserContext.getCurrentRetailer() != null) {
        requestTemplate.header(Globals.Headers.RETAILER_HEADER, UserContext.getCurrentRetailer());
      }
      if (UserContext.getCurrentWarehouse() != null) {
        requestTemplate.header(
            Globals.Headers.WAREHOUSE_HEADER, UserContext.getCurrentWarehouse().toString());
      }
    };
  }

  @Bean
  Logger feignLogger() {
    return new CustomFeignLogger();
  }

  @Value("${feign.client.log-level:BASIC}")
  private String feignLoggerLevel;

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.valueOf(feignLoggerLevel);
  }
}
