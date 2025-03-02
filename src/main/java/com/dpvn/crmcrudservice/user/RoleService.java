package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.domain.entity.Role;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.crmcrudservice.repository.RoleRepository;
import com.dpvn.crmcrudservice.repository.UserCustomRepository;
import com.dpvn.crmcrudservice.repository.UserRepository;
import com.dpvn.shared.exception.BadRequestException;
import com.dpvn.shared.repository.AbstractRepository;
import com.dpvn.shared.service.AbstractCrudService;
import com.dpvn.shared.util.ObjectUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
