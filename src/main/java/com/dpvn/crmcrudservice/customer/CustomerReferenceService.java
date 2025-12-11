package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.client.StorageClient;
import com.dpvn.crmcrudservice.domain.entity.CustomerReference;
import com.dpvn.crmcrudservice.repository.CustomerReferenceRepository;
import com.dpvn.sharedcore.domain.constant.Globals;
import com.dpvn.sharedcore.service.AbstractService;
import com.dpvn.sharedcore.util.DateUtil;
import com.dpvn.sharedcore.util.FastMap;
import com.dpvn.sharedcore.util.StringUtil;
import com.dpvn.storageservice.domain.dto.FileDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CustomerReferenceService extends AbstractService {
  private final CustomerReferenceRepository customerReferenceRepository;
  private final StorageClient storageClient;

  public CustomerReferenceService(
      CustomerReferenceRepository customerReferenceRepository, StorageClient storageClient) {
    this.customerReferenceRepository = customerReferenceRepository;
    this.storageClient = storageClient;
  }

  // hình như đang sai vòng while, thấy chạy hoài ko dừng
  @Deprecated
  public void correctCustomerReference() {
    int page = 0;
    while (true) {
      LOGGER.info("Fetching customer references page {}", page);
      boolean isContinue = getCustomerReferences(page);
      page++;
      if (!isContinue) {
        break;
      }
    }

    customerReferenceRepository.deleteCustomerReferenceByValueIsNull();
  }

  private boolean getCustomerReferences(int page) {
    Pageable pageable = PageRequest.of(page, Globals.Paging.FETCHING_PAGE_SIZE, Sort.by("id"));
    Page<CustomerReference> customerReferences =
        customerReferenceRepository.findAllCustomerReferencesWithPaperCode(pageable);

    List<CustomerReference> updated = new ArrayList<>();
    for (CustomerReference cr : customerReferences.getContent()) {
      String source = cr.getReference();
      String slug = cr.getValue();

      if (StringUtil.isNotEmpty(source) && StringUtil.isNotEmpty(slug)) {
        if (!fileExists(slug)) {
          LOGGER.info("File not found with slug = {}, uploading file from url {}", slug, source);

          // Feign call - MUST BE OUTSIDE transaction
          FileDto fileDto = uploadFileFromUrl(source);

          if (fileDto != null) {
            cr.setValue(fileDto.getSlug());
          } else {
            cr.setValue(null);
          }

          cr.setModifiedDate(DateUtil.now());
          updated.add(cr);
        } else {
          LOGGER.info("File is found with slug = {}, IGNORE to store anything", slug);
        }
      }
    }

    // short transaction
    if (!updated.isEmpty()) {
      customerReferenceRepository.saveAllAndFlush(updated);
    }

    return customerReferences.getSize() == Globals.Paging.FETCHING_PAGE_SIZE;
  }

  private boolean fileExists(String slug) {
    try {
      return storageClient.headFile(slug).getStatusCode().is2xxSuccessful();
    } catch (Exception e) {
      return false;
    }
  }

  private FileDto uploadFileFromUrl(String url) {
    try {
      return storageClient.uploadFileFromUrl(FastMap.create().add("url", url));
    } catch (Exception e) {
      return null;
    }
  }
}
