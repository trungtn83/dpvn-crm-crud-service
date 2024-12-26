package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.shared.repository.AbstractRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleCustomerRepository extends AbstractRepository<SaleCustomer> {
  List<SaleCustomer> findBySaleIdAndCustomerId(Long saleId, Long customerId);

  SaleCustomer findBySaleIdAndCustomerIdAndActive(Long saleId, Long customerId, Boolean status);
}
