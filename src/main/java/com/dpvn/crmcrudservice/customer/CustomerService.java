package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.AbstractService;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<Customer> {

  public CustomerService(CustomerRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<Customer> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public List<Customer> findByIds(List<Long> ids) {
    return ((CustomerRepository) repository).findByIdIn(ids);
  }
}
