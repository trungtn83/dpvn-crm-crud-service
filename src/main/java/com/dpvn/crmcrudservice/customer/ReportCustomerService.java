package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportCustomerService {
  private final CustomerRepository customerRepository;

  public ReportCustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }
}
