package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Department;
import com.dpvn.crmcrudservice.domain.entity.Role;
import com.dpvn.shared.domain.entity.Address;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheEntityService {
  private final AddressRepository addressRepository;
  private final DepartmentRepository departmentRepository;
  private final RoleRepository roleRepository;

  public CacheEntityService(
      AddressRepository addressRepository,
      DepartmentRepository departmentRepository,
      RoleRepository roleRepository) {
    this.addressRepository = addressRepository;
    this.departmentRepository = departmentRepository;
    this.roleRepository = roleRepository;
  }

  @Cacheable(cacheNames = "addresses", key = "'addresses'")
  public List<Address> getAddresses() {
    return addressRepository.findAll();
  }

  @Cacheable(cacheNames = "departments", key = "'departments'")
  public List<Department> getDepartments() {
    return departmentRepository.findAll();
  }

  @Cacheable(cacheNames = "roles", key = "'roles'")
  public List<Role> getRoles() {
    return roleRepository.findAll();
  }
}
