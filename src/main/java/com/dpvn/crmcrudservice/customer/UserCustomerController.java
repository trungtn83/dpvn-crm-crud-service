package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.AbstractController;
import com.dpvn.crmcrudservice.domain.dto.UserCustomerDto;
import com.dpvn.crmcrudservice.domain.entity.UserCustomer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-customer")
public class UserCustomerController extends AbstractController<UserCustomer, UserCustomerDto> {
  public UserCustomerController(UserCustomerService userCustomerService) {
    super(userCustomerService);
  }
}
