// package com.dpvn.crmcrudservice.customerold;
//
// import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
// import com.dpvn.crmcrudservice.domain.entity.SaleCustomerState;
// import com.dpvn.sharedjpa.controller.AbstractCrudController;
// import com.dpvn.sharedcore.util.FastMap;
// import java.util.List;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
//
// @RestController
// @RequestMapping("/sale-customer")
// public class SaleCustomerController extends AbstractCrudController<SaleCustomer> {
//  private final SaleCustomerStateService saleCustomerStateService;
//
//  public SaleCustomerController(
//      SaleCustomerService service, SaleCustomerStateService saleCustomerStateService) {
//    super(service);
//    this.saleCustomerStateService = saleCustomerStateService;
//  }
//
//  @PostMapping("/remove-by-options")
//  public void removeSaleCustomerByOptions(@RequestBody String dto) {
//    SaleCustomer entity = SaleCustomer.fromJson(dto);
//    ((SaleCustomerService) service).removeSaleCustomerByOptions(entity);
//  }
//
//  /**
//   * @param body: all criteria will be applied if exited and not null, empty
//   * - saleId: Long
//   * - customerIds: List<Long> find customer id in this list
//   * - relationshipType: Integer
//   * - reasonIds: List<Long> find reason id in this list
//   * - reasonRef: String
//   */
//  @PostMapping("/find-by-options")
//  public List<String> findSaleCustomersByOptions(@RequestBody FastMap body) {
//    Long saleId = body.getLong("saleId");
//    List<Long> customerIds = body.getList("customerIds");
//    Integer relationshipType = body.getInt("relationshipType");
//    List<Integer> reasonIds = body.getList("reasonIds");
//    String reasonRef = body.getString("reasonRef");
//
//    return ((SaleCustomerService) service)
//            .findSaleCustomersByOptions(saleId, customerIds, relationshipType, reasonIds,
// reasonRef)
//            .stream()
//            .map(SaleCustomer::toJson)
//            .toList();
//  }
//
//  @GetMapping("/state/by-sale")
//  public String getSaleCustomerStateBySale(
//      @RequestParam Long saleId, @RequestParam Long customerId) {
//    // TODO: consider to create default state here???
//    return saleCustomerStateService.getSaleCustomerStateBySale(saleId, customerId).toJson();
//  }
//
//  @PostMapping("/state")
//  public void upsertSaleCustomerState(@RequestBody String body) {
//    SaleCustomerState entity = SaleCustomerState.fromJson(body);
//    if (entity.getId() == null) {
//      saleCustomerStateService.create(entity);
//    } else {
//      saleCustomerStateService.save(entity);
//    }
//  }
//
//  @PostMapping("/state/find-latest")
//  public List<String> findLatestBySaleIdAndCustomerIds(@RequestBody FastMap body) {
//    Long saleId = body.getLong("saleId");
//    List<Long> customerIds = body.getList("customerIds");
//    return saleCustomerStateService.findLatestBySaleIdAndCustomerIds(saleId, customerIds).stream()
//        .map(SaleCustomerState::toJson)
//        .toList();
//  }
// }
