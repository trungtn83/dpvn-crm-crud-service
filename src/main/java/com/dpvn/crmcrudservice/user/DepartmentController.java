package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.domain.dto.DepartmentDto;
import com.dpvn.crmcrudservice.domain.dto.RoleDto;
import com.dpvn.crmcrudservice.domain.entity.Department;
import com.dpvn.crmcrudservice.domain.entity.Role;
import com.dpvn.shared.controller.AbstractCrudController;
import com.dpvn.shared.service.AbstractCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController extends AbstractCrudController<Department, DepartmentDto> {

  public DepartmentController(DepartmentService service) {
    super(service);
  }
}
