// package com.dpvn.crmcrudservice.customerold;
//
// import com.dpvn.crmcrudservice.domain.dto.CustomerDto;
// import com.dpvn.crmcrudservice.domain.entity.Customer;
// import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
// import com.dpvn.crmcrudservice.domain.mapper.CustomerMapper;
// import com.dpvn.sharedjpa.controller.AbstractCrudController;
// import com.dpvn.sharedcore.util.FastMap;
// import java.time.Instant;
// import java.util.List;
// import org.springframework.data.domain.Page;
// import org.springframework.web.bind.annotation.*;
//
// @RestController
// @RequestMapping("/customer")
// public class CustomerController extends AbstractCrudController<Customer, CustomerDto> {
//
//  public CustomerController(CustomerMapper mapper, CustomerService service) {
//    super(mapper, service);
//  }
//
//  /**
//   * @param mobilePhone
//   * @return Find customer by mobile phone in main contact, in additional phone and zalo from
//   *     references
//   */
//  @GetMapping("/find-by-mobile-phone")
//  public List<String> findByMobilePhone(@RequestParam("mobilePhone") String mobilePhone) {
//    List<Customer> customers = ((CustomerService)
// service).findCustomersByMobilePhone(mobilePhone);
//    return customers.stream().map(Customer::toJson).toList();
//  }
//
//  @PostMapping("/find-by-ids")
//  public List<String> findByIds(@RequestBody List<Long> ids) {
//    return ((CustomerService) service).findByIds(ids).stream().map(Customer::toJson).toList();
//  }
//
//  @GetMapping("/find-by-status-for-init")
//  public List<String> findByStatusForInitRelationship(
//      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
//      @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize)
// {
//    List<Customer> customers =
//        ((CustomerService) service).findByStatusForInitRelationship(page, pageSize);
//    return customers.stream().map(Customer::toJson).toList();
//  }
//
//  @PostMapping("/assign")
//  public void assignCustomer(@RequestBody FastMap body) {
//    Long saleId = body.getLong("saleId");
//    Long customerId = body.getLong("customerId");
//    SaleCustomer entity = SaleCustomer.fromJson(body.getString("info"));
//    ((CustomerService) service).assign(saleId, customerId, entity);
//  }
//
//  @PostMapping("/revoke")
//  public void revokeCustomer(@RequestBody FastMap body) {
//    Long saleId = body.getLong("saleId");
//    Long customerId = body.getLong("customerId");
//    ((CustomerService) service).revoke(saleId, customerId);
//  }
//
//  /**
//   * @param body
//   * @return List of error customer that can not be assigned to sale
//   */
//  @PostMapping("/assigns")
//  public List<Long> assignCustomers(@RequestBody FastMap body) {
//    Long saleId = body.getLong("saleId");
//    List<Long> customerIds = body.getList("customerIds");
//    SaleCustomer entity = SaleCustomer.fromJson(body.getString("info"));
//    return ((CustomerService) service).assigns(saleId, customerIds, entity);
//  }
//
//  @PostMapping("/revokes")
//  public List<Long> revokeCustomers(@RequestBody FastMap body) {
//    Long saleId = body.getLong("saleId");
//    List<Long> customerIds = body.getList("customerIds");
//    return ((CustomerService) service).revokes(saleId, customerIds);
//  }
//
//  @PostMapping("/my")
//  public FastMap findMyCustomers(@RequestBody FastMap body) {
//    List<Long> saleIds = body.getListClass("saleIds", Long.class);
//    Long customerTypeId = body.getLong("customerTypeId");
//    Long customerCategoryId = body.getLong("customerCategoryId");
//    String filterText = body.getString("filterText");
//    List<Integer> reasonIds = body.getList("reasonIds");
//
//    Integer page = body.getInt(0,  "page");
//    Integer pageSize = body.getInt(100, "pageSize");
//
//    Page<FastMap> customerPage =
//        ((CustomerService) service)
//            .findMyCustomers(
//                saleIds, customerTypeId, customerCategoryId, filterText, reasonIds, page,
// pageSize);
//    return FastMap.create()
//        .add("rows", customerPage.getContent())
//        .add("total", customerPage.getTotalElements())
//        .add("pageSize", customerPage.getSize())
//        .add("page", customerPage.getNumber());
//  }
//
//  @PostMapping("/in-pool")
//  public FastMap findInPoolCustomers(@RequestBody FastMap body) {
//    Long saleId = body.getLong("saleId");
//    String filterText = body.getString("filterText");
//    List<String> tags = body.getList("tags");
//    List<Long> typeIds = body.getList("typeIds");
//    List<String> locationCodes = body.getList("locationCodes");
//    List<Integer> sourceIds = body.getList("sourceIds");
//
//    Integer page = body.getInt("page");
//    Integer pageSize = body.getInt("pageSize");
//
//    Page<FastMap> customerPage =
//        ((CustomerService) service)
//            .findInPoolCustomers(
//                saleId, filterText, tags, typeIds, locationCodes, sourceIds, page, pageSize);
//    return FastMap.create()
//        .add("rows", customerPage.getContent())
//        .add("total", customerPage.getTotalElements())
//        .add("pageSize", customerPage.getSize())
//        .add("page", customerPage.getNumber());
//  }
//
//  @PostMapping("/in-ocean")
//  public FastMap findInOceanCustomers(@RequestBody FastMap body) {
//    Long saleId = body.getLong("saleId");
//    String filterText = body.getString("filterText");
//    List<Long> typeIds = body.getList("typeIds");
//    List<String> locationCodes = body.getList("locationCodes");
//    List<Integer> sourceIds = body.getList("sourceIds");
//    Integer page = body.getInt("page");
//    Integer pageSize = body.getInt("pageSize");
//
//    Page<Customer> customerPage =
//        ((CustomerService) service)
//            .findInOceanCustomers(
//                saleId, filterText, typeIds, locationCodes, sourceIds, page, pageSize);
//    return FastMap.create()
//        .add("rows", customerPage.stream().map(Customer::toJson).toList())
//        .add("total", customerPage.getTotalElements())
//        .add("pageSize", customerPage.getSize())
//        .add("page", customerPage.getNumber());
//  }
//
//  @PostMapping("/task-based")
//  public FastMap findTaskBasedCustomers(@RequestBody FastMap body) {
//    Long saleId = body.getLong("saleId");
//    String filterText = body.getString("filterText");
//    List<String> tags = body.getList("tags");
//
//    Integer page = body.getInt("page");
//    Integer pageSize = body.getInt("pageSize");
//
//    Page<FastMap> customerPage =
//        ((CustomerService) service)
//            .findTaskBasedCustomers(saleId, filterText, tags, page, pageSize);
//    return FastMap.create()
//        .add("rows", customerPage.getContent())
//        .add("total", customerPage.getTotalElements())
//        .add("pageSize", customerPage.getSize())
//        .add("page", customerPage.getNumber());
//  }
//
//  @PostMapping("/{id}/update-last-transaction")
//  public void updateLastTransaction(@PathVariable Long id, @RequestBody FastMap body) {
//    Instant lastTransaction = body.getInstant("lastTransaction");
//    boolean isSuccessful = body.getBoolean("isSuccessful");
//    ((CustomerService) service).updateLastTransaction(id, lastTransaction, isSuccessful);
//  }
//
//  // sourceId = 1, KIOTVIET, = 2, CRAFTONLINE...
//  // sourceId = null, find last created customer from all sources
//  @GetMapping("/find-last-created")
//  public String findLastCreatedCustomer(
//      @RequestParam(value = "sourceId", required = false) Integer sourceId) {
//    Customer customer = ((CustomerService) service).findLastCreatedCustomerBySource(sourceId);
//    if (customer == null) {
//      return null;
//    }
//    return customer.toJson();
//  }
//
//  @PostMapping("/{id}/approve")
//  public void approveCustomerFromSandToGold(@PathVariable Long id, @RequestBody FastMap body) {
//    Long userId = body.getLong("userId");
//    Boolean approved = body.getBoolean("approved");
//    Integer dispatchTypeId = body.getInt("dispatchTypeId");
//    Long saleId = body.getLong("saleId");
//    ((CustomerService) service)
//        .approveCustomerFromSandToGold(userId, id, approved, dispatchTypeId, saleId);
//  }
// }
