package com.dpvn.crmcrudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.dpvn")
@EnableDiscoveryClient
public class CrmCrudServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrmCrudServiceApplication.class, args);
  }
}
