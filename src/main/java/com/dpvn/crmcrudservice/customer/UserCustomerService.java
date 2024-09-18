package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.AbstractService;
import com.dpvn.crmcrudservice.domain.entity.UserCustomer;
import com.dpvn.crmcrudservice.repository.UserCustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserCustomerService extends AbstractService<UserCustomer> {

  public UserCustomerService(UserCustomerRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<UserCustomer> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
