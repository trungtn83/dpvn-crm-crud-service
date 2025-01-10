package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.dto.CustomerDto;
import com.dpvn.crmcrudservice.domain.dto.CustomerTypeDto;
import com.dpvn.crmcrudservice.domain.dto.SaleCustomerDto;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.CustomerType;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.shared.controller.AbstractCrudController;
import com.dpvn.shared.util.FastMap;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/customer-type")
public class CustomerTypeController extends AbstractCrudController<CustomerType, CustomerTypeDto> {

  public CustomerTypeController(CustomerTypeService customerTypeService) {
    super(customerTypeService);
  }

}
