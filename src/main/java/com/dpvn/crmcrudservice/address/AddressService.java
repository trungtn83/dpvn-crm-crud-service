package com.dpvn.crmcrudservice.address;

import com.dpvn.crmcrudservice.repository.AddressRepository;
import com.dpvn.shared.domain.entity.Address;
import com.dpvn.shared.service.AbstractService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends AbstractService<Address> {
  public AddressService(AddressRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<Address> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
