package com.dpvn.crmcrudservice.client;

import com.dpvn.reportcrudservice.domain.dto.UserDto;
import com.dpvn.sharedcore.config.FeignCommonConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "report-crud-service",
    contextId = "report-crud-service-client",
    configuration = FeignCommonConfig.class)
public interface ReportCrudServiceClient {
  @GetMapping("/user/{id}")
  UserDto getUserById(@PathVariable Long id);
}
