package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.dto.SaleCustomerReferenceDto;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomerReference;
import com.dpvn.crmcrudservice.domain.mapper.SaleCustomerReferenceMapper;
import com.dpvn.sharedjpa.controller.AbstractCrudController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/sale-customer/reference")
public class SaleCustomerReferenceController
    extends AbstractCrudController<SaleCustomerReference, SaleCustomerReferenceDto> {

  private final SaleCustomerReferenceService saleCustomerReferenceService;

  public SaleCustomerReferenceController(
      SaleCustomerReferenceMapper mapper,
      SaleCustomerReferenceService service,
      SaleCustomerReferenceService saleCustomerReferenceService) {
    super(mapper, service);
    this.saleCustomerReferenceService = saleCustomerReferenceService;
  }

  @PostMapping("/upsert")
  public void upsertSaleCustomerReference(@RequestBody SaleCustomerReferenceDto dto) {
    saleCustomerReferenceService.upsertSaleCustomerReference(mapper.toEntity(dto));
  }
}
