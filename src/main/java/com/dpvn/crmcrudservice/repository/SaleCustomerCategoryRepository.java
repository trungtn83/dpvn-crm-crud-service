package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomerCategory;
import com.dpvn.sharedjpa.repository.AbstractRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleCustomerCategoryRepository extends AbstractRepository<SaleCustomerCategory> {
  List<SaleCustomerCategory> findAllBySaleId(Long saleId);
}
