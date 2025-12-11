package com.dpvn.crmcrudservice.customer;

import com.dpvn.apigateway.domain.Headers;
import com.dpvn.crmcrudservice.domain.dto.CustomerDto;
import com.dpvn.crmcrudservice.domain.dto.CustomerStatusDto;
import com.dpvn.crmcrudservice.domain.dto.GoldCustomerDto;
import com.dpvn.crmcrudservice.domain.dto.GoldMineCustomerDto;
import com.dpvn.crmcrudservice.domain.dto.TreasureCustomerDto;
import com.dpvn.crmcrudservice.domain.dto.ViewCustomerDetailDto;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.CustomerStatus;
import com.dpvn.crmcrudservice.domain.mapper.CustomerMapper;
import com.dpvn.crmcrudservice.domain.mapper.CustomerStatusMapper;
import com.dpvn.sharedcore.domain.constant.Globals;
import com.dpvn.sharedcore.domain.dto.PagingResponse;
import com.dpvn.sharedcore.util.FastMap;
import com.dpvn.sharedjpa.controller.AbstractCrudController;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/customer")
public class CustomerController extends AbstractCrudController<Customer, CustomerDto> {

  private final CustomerService customerService;
  private final CustomerStatusMapper customerStatusMapper;
  private final CustomerReferenceService customerReferenceService;

  public CustomerController(
      CustomerMapper mapper,
      CustomerService service,
      CustomerService customerService,
      CustomerStatusMapper customerStatusMapper,
      CustomerReferenceService customerReferenceService) {
    super(mapper, service);
    this.customerService = customerService;
    this.customerStatusMapper = customerStatusMapper;
    this.customerReferenceService = customerReferenceService;
  }

  @GetMapping("/{id}/status")
  public CustomerStatusDto getCustomerStatus(@PathVariable Long id) {
    CustomerStatus customerStatus = ((CustomerService) service).getCustomerStatus(id);
    return customerStatusMapper.toDto(customerStatus);
  }

  @GetMapping("/{id}/view")
  public ViewCustomerDetailDto getCustomerDetailView(
      @RequestHeader(value = Headers.X_USER_ID) Long userId, @PathVariable Long id) {
    return customerService.getCustomerDetailView(userId, id);
  }

  @PostMapping("/my-treasure")
  public PagingResponse<TreasureCustomerDto> findAllMyTreasureCustomers(@RequestBody FastMap body) {
    int page = body.getInt(0, "page");
    int pageSize = body.getInt(Globals.Paging.PAGE_SIZE, "pageSize");
    Long saleId = body.getLong("saleId");
    Long customerTypeId = body.getLong("customerTypeId");
    String filterText = body.getString("filterText");
    return customerService.findAllMyTreasureCustomers(
        saleId, customerTypeId, filterText, page, pageSize);
  }

  @PostMapping("/my-gold")
  public PagingResponse<GoldCustomerDto> findAllMyGoldCustomers(@RequestBody FastMap body) {
    int page = body.getInt(0, "page");
    int pageSize = body.getInt(Globals.Paging.PAGE_SIZE, "pageSize");
    Long saleId = body.getLong("saleId");
    Long customerTypeId = body.getLong("customerTypeId");
    Long saleCustomerCategoryId = body.getLong("saleCustomerCategoryId");
    List<String> tags = body.getListClass("tags", String.class);
    String filterText = body.getString("filterText");
    return customerService.findAllMyGoldCustomers(
        saleId, customerTypeId, saleCustomerCategoryId, tags, filterText, page, pageSize);
  }

  @PostMapping("/in-goldmine")
  public PagingResponse<GoldMineCustomerDto> findAllCustomersInGoldMine(@RequestBody FastMap body) {
    int page = body.getInt(0, "page");
    int pageSize = body.getInt(Globals.Paging.PAGE_SIZE, "pageSize");
    Long saleId = body.getLong("saleId");
    Long sourceId = body.getLong("sourceId");
    Long customerTypeId = body.getLong("customerTypeId");
    List<String> tags = body.getListClass("tags", String.class);
    String filterText = body.getString("filterText");
    return customerService.findAllInGoldMineCustomers(
        saleId, sourceId, customerTypeId, tags, filterText, page, pageSize);
  }

  @PostMapping("/in-sandbank")
  public PagingResponse<CustomerDto> findAllCustomersInSandBank(@RequestBody FastMap body) {
    int page = body.getInt(0, "page");
    int pageSize = body.getInt(Globals.Paging.PAGE_SIZE, "pageSize");
    Long sourceId = body.getLong("sourceId");
    Long customerTypeId = body.getLong("customerTypeId");
    String filterText = body.getString("filterText");
    return customerService.findAllInSandBankCustomers(
        sourceId, customerTypeId, filterText, page, pageSize);
  }

  @PostMapping("/report-by-sellers")
  public Map<Long, List<FastMap>> reportCustomersBySellersInRange(@RequestBody FastMap body) {
    List<Long> sellerIds = body.getListClass("saleIds", Long.class);
    String fromDate = body.getString("fromDate");
    String toDate = body.getString("toDate");
    return ((CustomerService) service).reportCustomersBySellersInRange(sellerIds, fromDate, toDate);
  }

  @Deprecated
  @PostMapping("/reference/correction")
  public void correctCustomerReference() {
    customerReferenceService.correctCustomerReference();
  }
}
