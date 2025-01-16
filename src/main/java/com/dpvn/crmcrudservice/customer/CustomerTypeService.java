package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.entity.CustomerType;
import com.dpvn.crmcrudservice.repository.CustomerTypeRepository;
import com.dpvn.shared.service.AbstractCrudService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerTypeService extends AbstractCrudService<CustomerType> {
  public CustomerTypeService(CustomerTypeRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<CustomerType> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
