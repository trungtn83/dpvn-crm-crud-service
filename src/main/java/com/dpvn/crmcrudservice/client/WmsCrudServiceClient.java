package com.dpvn.crmcrudservice.client;

import com.dpvn.shared.util.FastMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wms-crud-service", contextId = "wms-crud-service-client")
public interface WmsCrudServiceClient {

  /**
   * Long oldCustomerIdf = body.getLong("oldCustomerIdf");
   * Long newCustomerIdf = body.getLong("newCustomerIdf");
   */
  @Async
  @PostMapping("/order/update-customer-idfs")
  void updateOrderCustomerIdfs(@RequestBody FastMap body);

  /**
   * Long oldCustomerIdf = body.getLong("oldCustomerIdf");
   * Long newCustomerIdf = body.getLong("newCustomerIdf");
   */
  @Async
  @PostMapping("/invoice/update-customer-idfs")
  void updateInvoiceCustomerIdfs(@RequestBody FastMap body);
}
