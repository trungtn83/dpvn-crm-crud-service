package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomerCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleCustomerCategoryRepository extends JpaRepository<SaleCustomerCategory, Long> {
  List<SaleCustomerCategory> findBySaleId(Long saleId);

  List<SaleCustomerCategory> findBySaleIdAndCode(Long saleId, String code);
}
