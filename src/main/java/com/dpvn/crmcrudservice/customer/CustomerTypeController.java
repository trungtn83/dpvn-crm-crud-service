package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.dto.CustomerTypeDto;
import com.dpvn.crmcrudservice.domain.entity.CustomerType;
import com.dpvn.crmcrudservice.domain.mapper.CustomerTypeMapper;
import com.dpvn.sharedjpa.controller.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/customer/type")
public class CustomerTypeController extends AbstractCrudController<CustomerType, CustomerTypeDto> {
  public CustomerTypeController(CustomerTypeMapper mapper, CustomerTypeService service) {
    super(mapper, service);
  }
}
