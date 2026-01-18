package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.dto.SaleCustomerStateDto;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomerState;
import com.dpvn.crmcrudservice.domain.mapper.SaleCustomerStateMapper;
import com.dpvn.sharedjpa.controller.AbstractCrudController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/sale-customer/state")
public class SaleCustomerStateController
    extends AbstractCrudController<SaleCustomerState, SaleCustomerStateDto> {

  public SaleCustomerStateController(
      SaleCustomerStateMapper mapper, SaleCustomerStateService service) {
    super(mapper, service);
  }

  @PostMapping("/upsert")
  public void upsertSaleCustomerState(@RequestBody SaleCustomerStateDto dto) {
    ((SaleCustomerStateService) service).upsert(mapper.toEntity(dto));
  }
}
