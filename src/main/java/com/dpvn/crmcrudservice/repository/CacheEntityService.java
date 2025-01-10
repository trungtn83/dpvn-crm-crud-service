package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.CustomerType;
import com.dpvn.crmcrudservice.domain.entity.Department;
import com.dpvn.crmcrudservice.domain.entity.Role;
import com.dpvn.shared.domain.entity.Address;
import java.util.Comparator;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheEntityService {
  private final AddressRepository addressRepository;
  private final DepartmentRepository departmentRepository;
  private final RoleRepository roleRepository;
  private final CustomerTypeRepository customerTypeRepository;

  public CacheEntityService(
      AddressRepository addressRepository,
      DepartmentRepository departmentRepository,
      RoleRepository roleRepository,
      CustomerTypeRepository customerTypeRepository) {
    this.addressRepository = addressRepository;
    this.departmentRepository = departmentRepository;
    this.roleRepository = roleRepository;
    this.customerTypeRepository = customerTypeRepository;
  }

  @Cacheable(cacheNames = "addresses", key = "'addresses'")
  public List<Address> getAddresses() {
    List<Address> addresses = addressRepository.findAll();
    addresses.sort(
        Comparator.comparingInt((Address a) -> a.getWardName().length())
            .thenComparingInt(a -> a.getDistrictName().length())
            .thenComparingInt(a -> a.getProvinceName().length()));
    return addresses;
  }

  @Cacheable(cacheNames = "departments", key = "'departments'")
  public List<Department> getDepartments() {
    return departmentRepository.findAll();
  }

  @Cacheable(cacheNames = "roles", key = "'roles'")
  public List<Role> getRoles() {
    return roleRepository.findAll();
  }

  //  @Cacheable(cacheNames = "customerTypes", key = "'customerTypes'")
  public List<CustomerType> getCustomerTypes() {
    return customerTypeRepository.findAll();
  }
}
