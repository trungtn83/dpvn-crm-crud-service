package com.dpvn.crmcrudservice.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class SqlTemplateLoader {
  @Bean
  public Map<String, String> sqlTemplates(ResourcePatternResolver resolver) throws IOException {
    Map<String, String> map = new HashMap<>();
    Resource[] resources = resolver.getResources("classpath:sql/*.sql");
    for (Resource r : resources) {
      String name = r.getFilename(); // ví dụ "gold_customers.sql"
      String content = new String(r.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
      map.put(name, content);
    }
    return map;
  }
}
