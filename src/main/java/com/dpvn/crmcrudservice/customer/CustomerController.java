package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.AbstractController;
import com.dpvn.crmcrudservice.domain.dto.CustomerDto;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.shared.util.FastMap;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController extends AbstractController<Customer, CustomerDto> {
  public CustomerController(CustomerService customerService) {
    super(customerService);
  }

  @PostMapping("/search")
  public List<CustomerDto> search(@RequestBody FastMap conditions) {
    List<Customer> customers = ((CustomerService) service).search(conditions);
    return customers.stream().map(Customer::toDto).toList();
  }
}
