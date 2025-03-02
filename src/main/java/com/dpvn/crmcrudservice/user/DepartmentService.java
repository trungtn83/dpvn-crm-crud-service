package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.domain.entity.Department;
import com.dpvn.crmcrudservice.domain.entity.Role;
import com.dpvn.crmcrudservice.repository.DepartmentRepository;
import com.dpvn.crmcrudservice.repository.RoleRepository;
import com.dpvn.shared.exception.BadRequestException;
import com.dpvn.shared.service.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService extends AbstractCrudService<Department> {
  public DepartmentService(DepartmentRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<Department> entities) {
    throw new BadRequestException("Not implemented yet");
  }
}
