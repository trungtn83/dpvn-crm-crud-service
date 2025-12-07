package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.repository.CustomerRepository;
import com.dpvn.sharedcore.util.DateUtil;
import com.dpvn.sharedcore.util.FastMap;
import com.dpvn.sharedcore.util.LocalDateUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportCustomerService {
  private final CustomerRepository customerRepository;

  public ReportCustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

}
