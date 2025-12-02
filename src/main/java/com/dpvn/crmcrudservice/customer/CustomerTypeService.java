package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.client.ReportCrudServiceClient;
import com.dpvn.crmcrudservice.domain.entity.CustomerType;
import com.dpvn.crmcrudservice.repository.CustomerTypeRepository;
import com.dpvn.reportcrudservice.domain.dto.UserDto;
import com.dpvn.sharedjpa.service.AbstractCrudService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerTypeService extends AbstractCrudService<CustomerType> {
  private final ReportCrudServiceClient reportCrudServiceClient;

  public CustomerTypeService(
      CustomerTypeRepository repository, ReportCrudServiceClient reportCrudServiceClient) {
    super(repository);
    this.reportCrudServiceClient = reportCrudServiceClient;
  }

  @Override
  public void sync(List<CustomerType> list) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public List<CustomerType> findAllCustomerTypes(Long userId) {
    UserDto userDto = reportCrudServiceClient.getUserById(userId);
    return repository.findAll();
  }
}
