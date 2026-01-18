package com.dpvn.crmcrudservice.client;

import com.dpvn.sharedcore.config.FeignCommonConfig;
import com.dpvn.sharedcore.config.FeignMultipartConfig;
import com.dpvn.sharedcore.util.FastMap;
import com.dpvn.storageservice.domain.dto.FileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
    name = "storage-service",
    contextId = "storage-service-client",
    url = "${storage-service.url}",
configuration = {
FeignMultipartConfig .class,
FeignCommonConfig .class
    })
public interface StorageClient {
  @PostMapping("/file/upload-from-url")
  FileDto uploadFileFromUrl(@RequestBody FastMap url);

  @RequestMapping(method = RequestMethod.HEAD, value = "/file/{slug}")
  ResponseEntity<Void> headFile(@PathVariable("slug") String slug);
}
