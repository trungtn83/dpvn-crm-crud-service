package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.dto.CustomerTypeDto;
import com.dpvn.crmcrudservice.domain.entity.CustomerType;
import com.dpvn.shared.controller.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer-type")
public class CustomerTypeController extends AbstractCrudController<CustomerType, CustomerTypeDto> {

  public CustomerTypeController(CustomerTypeService customerTypeService) {
    super(customerTypeService);
  }
}
