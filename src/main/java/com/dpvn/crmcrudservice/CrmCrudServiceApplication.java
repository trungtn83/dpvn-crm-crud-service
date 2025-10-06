package com.dpvn.crmcrudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@EntityScan(basePackages = "com.dpvn")
@ComponentScan(basePackages = "com.dpvn")
@EnableJpaRepositories(basePackages = "com.dpvn")
@SpringBootApplication
@EnableDiscoveryClient
public class CrmCrudServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrmCrudServiceApplication.class, args);
  }
}
