package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.domain.entity.Role;
import com.dpvn.crmcrudservice.repository.RoleRepository;
import com.dpvn.shared.exception.BadRequestException;
import com.dpvn.shared.service.AbstractCrudService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends AbstractCrudService<Role> {
  public RoleService(RoleRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<Role> entities) {
    throw new BadRequestException("Not implemented yet");
  }
}
