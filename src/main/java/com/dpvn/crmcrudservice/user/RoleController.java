package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.domain.dto.RoleDto;
import com.dpvn.crmcrudservice.domain.entity.Role;
import com.dpvn.shared.controller.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController extends AbstractCrudController<Role, RoleDto> {

  public RoleController(RoleService service) {
    super(service);
  }
}
