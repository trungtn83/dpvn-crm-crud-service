package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
  Department findByDepartmentName(String departmentName);
}
