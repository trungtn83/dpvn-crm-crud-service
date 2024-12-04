package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleCustomerRepository extends JpaRepository<SaleCustomer, Long> {
  List<SaleCustomer> findBySaleIdAndCustomerId(Long saleId, Long customerId);

  SaleCustomer findBySaleIdAndCustomerIdAndStatus(Long saleId, Long customerId, Integer status);
}
