package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.UserCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCustomerRepository extends JpaRepository<UserCustomer, Long> {}
