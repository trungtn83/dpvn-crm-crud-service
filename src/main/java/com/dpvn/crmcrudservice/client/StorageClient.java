package com.dpvn.crmcrudservice.client;

import com.dpvn.shared.util.FastMap;
import com.dpvn.storageservice.domain.FileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "storage-service",
    contextId = "storage-service-client",
    url = "${storage-service.url}")
public interface StorageClient {
  @PostMapping("/file/upload-from-url")
  FileDto uploadFileFromUrl(@RequestBody FastMap url);
}
