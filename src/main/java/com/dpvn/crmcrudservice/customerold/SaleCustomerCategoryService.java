// package com.dpvn.crmcrudservice.customerold;
//
// import com.dpvn.crmcrudservice.domain.entity.SaleCustomerCategory;
// import com.dpvn.crmcrudservice.repository.SaleCustomerCategoryRepository;
// import com.dpvn.sharedjpa.service.AbstractCrudService;
// import java.util.List;
// import org.springframework.stereotype.Service;
//
// @Service
// public class SaleCustomerCategoryService extends AbstractCrudService<SaleCustomerCategory> {
//  public SaleCustomerCategoryService(SaleCustomerCategoryRepository repository) {
//    super(repository);
//  }
//
//  @Override
//  public void sync(List<SaleCustomerCategory> entities) {
//    throw new UnsupportedOperationException("Not supported yet.");
//  }
//
//  public List<SaleCustomerCategory> findBySaleId(Long saleId) {
//    return ((SaleCustomerCategoryRepository) repository).findBySaleId(saleId);
//  }
//
//  public List<SaleCustomerCategory> findByCode(Long saleId, String code) {
//    return ((SaleCustomerCategoryRepository) repository).findBySaleIdAndCode(saleId, code);
//  }
// }
