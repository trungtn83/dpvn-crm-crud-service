package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomerCategory;
import com.dpvn.crmcrudservice.repository.SaleCustomerCategoryRepository;
import com.dpvn.sharedjpa.service.AbstractCrudService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SaleCustomerCategoryService extends AbstractCrudService<SaleCustomerCategory> {
  public SaleCustomerCategoryService(SaleCustomerCategoryRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<SaleCustomerCategory> list) {}

  public List<SaleCustomerCategory> findAllSaleCustomerCategoriesBySale(Long saleId) {
    return ((SaleCustomerCategoryRepository) repository).findAllBySaleId(saleId);
  }
}
