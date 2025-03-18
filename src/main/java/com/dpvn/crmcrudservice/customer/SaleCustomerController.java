package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.dto.SaleCustomerDto;
import com.dpvn.crmcrudservice.domain.dto.SaleCustomerStateDto;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomerState;
import com.dpvn.shared.controller.AbstractCrudController;
import com.dpvn.shared.util.DateUtil;
import com.dpvn.shared.util.FastMap;
import com.dpvn.shared.util.LocalDateUtil;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale-customer")
public class SaleCustomerController extends AbstractCrudController<SaleCustomer, SaleCustomerDto> {
  protected static final Logger LOGGER = LoggerFactory.getLogger(SaleCustomerController.class);
  private final SaleCustomerStateService saleCustomerStateService;
  private final SaleCustomerService saleCustomerService;

  public SaleCustomerController(
      SaleCustomerService service,
      SaleCustomerStateService saleCustomerStateService,
      SaleCustomerService saleCustomerService) {
    super(service);
    this.saleCustomerStateService = saleCustomerStateService;
    this.saleCustomerService = saleCustomerService;
  }

  @PostMapping("/remove-by-options")
  public void removeSaleCustomerByOptions(@RequestBody SaleCustomerDto dto) {
    ((SaleCustomerService) service).removeSaleCustomerByOptions(dto);
  }

  /**
   * @param body: all criteria will be applied if exited and not null, empty
   * - saleId: Long
   * - customerIds: List<Long> find customer id in this list
   * - relationshipType: Integer
   * - reasonIds: List<Long> find reason id in this list
   * - reasonRef: String
   */
  @PostMapping("/find-by-options")
  public List<SaleCustomerDto> findSaleCustomersByOptions(@RequestBody FastMap body) {
    Long saleId = body.getLong("saleId");
    List<Long> customerIds = body.getList("customerIds");
    Integer relationshipType = body.getInt("relationshipType");
    List<Integer> reasonIds = body.getList("reasonIds");
    String reasonRef = body.getString("reasonRef");

    return ((SaleCustomerService) service)
            .findSaleCustomersByOptions(saleId, customerIds, relationshipType, reasonIds, reasonRef)
            .stream()
            .map(SaleCustomer::toDto)
            .toList();
  }

  @GetMapping("/state/by-sale")
  public SaleCustomerStateDto getSaleCustomerStateBySale(
      @RequestParam Long saleId, @RequestParam Long customerId) {
    // TODO: consider to create default state here???
    return saleCustomerStateService.getSaleCustomerStateBySale(saleId, customerId).toDto();
  }

  @PostMapping("/state")
  public void upsertSaleCustomerState(@RequestBody SaleCustomerStateDto body) {
    if (body.getId() == null) {
      saleCustomerStateService.create(body.toEntity());
    } else {
      saleCustomerStateService.save(body.toEntity());
    }
  }

  @PostMapping("/state/find-latest")
  public List<SaleCustomerStateDto> findLatestBySaleIdAndCustomerIds(@RequestBody FastMap body) {
    Long saleId = body.getLong("saleId");
    List<Long> customerIds = body.getList("customerIds");
    return saleCustomerStateService.findLatestBySaleIdAndCustomerIds(saleId, customerIds).stream()
        .map(SaleCustomerState::toDto)
        .toList();
  }

  /**
   * - saleId: Long
   * - fromDate: string -> yyyy-MM-dd
   * - toDate: string -> yyyy-MM-dd
   */
  //  @PostMapping("/find-by-sale")
  //  public List<SaleCustomerDto> findSaleCustomerBySale(@RequestBody FastMap body) {
  //    Date now = new Date();
  //    Long saleId = body.getLong("saleId");
  //    String fromDateStr = body.getString("fromDate");
  //    String toDateStr = body.getString("toDate");
  //    List<SaleCustomerDto> result = saleCustomerService
  //        .findSaleCustomersBySale(
  //            saleId,
  //            DateUtil.from(LocalDateUtil.from(fromDateStr)),
  //            DateUtil.from(LocalDateUtil.from(toDateStr)))
  //        .stream()
  //        .map(SaleCustomer::toDto)
  //        .toList();
  //    long diff = (new Date()).getTime() - now.getTime();
  //    LOGGER.info("findSaleCustomerBySale: [{}-{}-{}]] = {}", saleId, fromDateStr, toDateStr,
  // diff);
  //    return result;
  //  }

  @PostMapping("/find-by-sale")
  public FastMap findSaleCustomerBySale(@RequestBody FastMap body) {
    Long saleId = body.getLong("saleId");
    String fromDateStr = body.getString("fromDate");
    String toDateStr = body.getString("toDate");
    Instant fromDate = DateUtil.from(LocalDateUtil.from(fromDateStr));
    Instant toDate = DateUtil.from(LocalDateUtil.from(toDateStr).plusDays(1));
    return saleCustomerService.findSaleCustomersBySale(saleId, fromDate, toDate);
  }
}
