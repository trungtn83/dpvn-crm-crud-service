package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.dto.CustomerDto;
import com.dpvn.crmcrudservice.domain.dto.SaleCustomerDto;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.shared.controller.AbstractCrudController;
import com.dpvn.shared.util.FastMap;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController extends AbstractCrudController<Customer, CustomerDto> {

  public CustomerController(CustomerService customerService) {
    super(customerService);
  }

  /**
   * @param mobilePhone
   * @return Find customer by mobile phone in main contact, in additional phone and zalo from
   *     references
   */
  @GetMapping("/find-by-mobile-phone")
  public List<CustomerDto> findByMobilePhone(@RequestParam("mobilePhone") String mobilePhone) {
    List<Customer> customers = ((CustomerService) service).findCustomersByMobilePhone(mobilePhone);
    return customers.stream().map(Customer::toDto).toList();
  }

  @GetMapping("/find-by-ids")
  public List<CustomerDto> findByIds(@RequestBody List<Long> ids) {
    return ((CustomerService) service).findByIds(ids).stream().map(Customer::toDto).toList();
  }

  /**
   * @param status can be null and return correct only null value
   */
  @GetMapping("/find-by-status")
  public List<CustomerDto> findByStatus(
      @RequestParam(value = "status", required = false) String status,
      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize) {
    List<Customer> customers = ((CustomerService) service).findByStatus(status, page, pageSize);
    return customers.stream().map(Customer::toDto).toList();
  }

  @PostMapping("/assign")
  public void assignCustomer(@RequestBody SaleCustomerDto body) {
    Long saleId = body.getSaleId();
    Long customerId = body.getCustomerId();
    SaleCustomer entity = body.toEntity();
    ((CustomerService) service).assign(saleId, customerId, entity);
  }

  @PostMapping("/revoke")
  public void revokeCustomer(@RequestBody SaleCustomerDto body) {
    Long saleId = body.getSaleId();
    Long customerId = body.getCustomerId();
    ((CustomerService) service).revoke(saleId, customerId);
  }

  /**
   * @param body
   * @return List of error customer that can not be assigned to sale
   */
  @PostMapping("/assigns")
  public List<Long> assignCustomers(@RequestBody FastMap body) {
    Long saleId = body.getLong("saleId");
    List<Long> customerIds = body.getList("customerIds");
    SaleCustomer entity = body.getClass("info", SaleCustomerDto.class).toEntity();
    return ((CustomerService) service).assigns(saleId, customerIds, entity);
  }

  @PostMapping("/revokes")
  public List<Long> revokeCustomers(@RequestBody FastMap body) {
    Long saleId = body.getLong("saleId");
    List<Long> customerIds = body.getList("customerIds");
    return ((CustomerService) service).revokes(saleId, customerIds);
  }

  @PostMapping("/my")
  public FastMap findMyCustomers(@RequestBody FastMap body) {
    Long saleId = body.getLong("saleId");
    Long customerCategoryId = body.getLong("customerCategoryId");
    String filterText = body.getString("filterText");
    List<Integer> reasonIds = body.getList("reasonIds");

    Integer page = body.getInt("page");
    Integer pageSize = body.getInt("pageSize");

    Page<FastMap> customerPage =
        ((CustomerService) service)
            .findMyCustomers(saleId, customerCategoryId, filterText, reasonIds, page, pageSize);
    return FastMap.create()
        .add("rows", customerPage.getContent())
        .add("total", customerPage.getTotalElements())
        .add("pageSize", customerPage.getSize())
        .add("page", customerPage.getNumber());
  }

  @PostMapping("/in-pool")
  public FastMap findInPoolCustomers(@RequestBody FastMap body) {
    Long saleId = body.getLong("saleId");
    String filterText = body.getString("filterText");
    List<String> tags = body.getList("tags");

    Integer page = body.getInt("page");
    Integer pageSize = body.getInt("pageSize");

    Page<FastMap> customerPage =
        ((CustomerService) service).findInPoolCustomers(saleId, filterText, tags, page, pageSize);
    return FastMap.create()
        .add("rows", customerPage.getContent())
        .add("total", customerPage.getTotalElements())
        .add("pageSize", customerPage.getSize())
        .add("page", customerPage.getNumber());
  }

  @PostMapping("/task-based")
  public FastMap findTaskBasedCustomers(@RequestBody FastMap body) {
    Long saleId = body.getLong("saleId");
    String filterText = body.getString("filterText");
    List<String> tags = body.getList("tags");

    Integer page = body.getInt("page");
    Integer pageSize = body.getInt("pageSize");

    Page<FastMap> customerPage =
        ((CustomerService) service)
            .findTaskBasedCustomers(saleId, filterText, tags, page, pageSize);
    return FastMap.create()
        .add("rows", customerPage.getContent())
        .add("total", customerPage.getTotalElements())
        .add("pageSize", customerPage.getSize())
        .add("page", customerPage.getNumber());
  }

  @PostMapping("/{id}/update-last-transaction")
  public void updateLastTransaction(@PathVariable Long id, @RequestBody FastMap body) {
    Instant lastTransaction = body.getInstant("lastTransaction");
    boolean isSuccessful = body.getBoolean("isSuccessful");
    ((CustomerService) service).updateLastTransaction(id, lastTransaction, isSuccessful);
  }

  @GetMapping("/find-last-created")
  public CustomerDto findLastCreatedCustomer() {
    Customer customer = ((CustomerService) service).findLastCreatedCustomer();
    if (customer == null) {
      return null;
    }
    return customer.toDto();
  }
}
