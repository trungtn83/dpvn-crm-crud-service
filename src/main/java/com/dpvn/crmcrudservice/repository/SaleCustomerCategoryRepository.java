package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleCustomerCategoryRepository extends JpaRepository<SaleCustomerCategory, Long> {
  List<SaleCustomerCategory> findBySaleId(Long saleId);
  List<SaleCustomerCategory> findBySaleIdAndCode(Long saleId, String code);
}
