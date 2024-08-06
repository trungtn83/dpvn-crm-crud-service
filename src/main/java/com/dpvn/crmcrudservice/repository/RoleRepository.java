package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByRoleName(String roleName);
}
