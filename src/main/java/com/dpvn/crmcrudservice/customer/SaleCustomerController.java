package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.dto.SaleCustomerDto;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.domain.mapper.SaleCustomerMapper;
import com.dpvn.sharedjpa.controller.AbstractCrudController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/sale-customer")
public class SaleCustomerController extends AbstractCrudController<SaleCustomer, SaleCustomerDto> {

  public SaleCustomerController(SaleCustomerMapper mapper, SaleCustomerService service) {
    super(mapper, service);
  }

  @PostMapping("/assign")
  public void assignSaleCustomer(@RequestBody SaleCustomerDto dto) {
    ((SaleCustomerService) service).upsertSaleCustomer(mapper.toEntity(dto));
  }

  @PostMapping("/revoke")
  public void revokeSaleCustomer(@RequestBody SaleCustomerDto dto) {
    ((SaleCustomerService) service).removeSaleCustomer(mapper.toEntity(dto));
  }
}
