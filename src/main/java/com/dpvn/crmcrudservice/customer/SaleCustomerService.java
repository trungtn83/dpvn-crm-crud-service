package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.repository.SaleCustomerCategoryRepository;
import com.dpvn.crmcrudservice.repository.SaleCustomerRepository;
import com.dpvn.sharedjpa.service.AbstractCrudService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SaleCustomerService extends AbstractCrudService<SaleCustomer> {

  private final SaleCustomerCategoryRepository saleCustomerCategoryRepository;

  public SaleCustomerService(
      SaleCustomerRepository repository,
      SaleCustomerCategoryRepository saleCustomerCategoryRepository) {
    super(repository);
    this.saleCustomerCategoryRepository = saleCustomerCategoryRepository;
  }

  @Override
  public void sync(List<SaleCustomer> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Thêm hoặc extend thời gian theo tiêu chí bắt buộc
   * SaleId, CustomerId, Status, Reason, ReasonRef
   */
  public void upsertSaleCustomer(SaleCustomer entity) {
    SaleCustomer dbEntity =
        ((SaleCustomerRepository) repository)
            .getByCustomerIdAndSaleIdAndStatusAndReasonCodeAndReasonRef(
                entity.getCustomerId(),
                entity.getSaleId(),
                entity.getStatus(),
                entity.getReasonCode(),
                entity.getReasonRef());
    if (dbEntity == null) {
      save(entity);
    } else {
      dbEntity.setAvailableFrom(entity.getAvailableFrom());
      dbEntity.setAvailableTo(entity.getAvailableTo());
      save(dbEntity);
    }
  }

  /**
   * Xóa quan hệ theo tiêu chí bắt buộc
   * SaleId, CustomerId, Status, Reason, ReasonRef
   */
  public void removeSaleCustomer(SaleCustomer entity) {
    SaleCustomer dbEntity =
        ((SaleCustomerRepository) repository)
            .getByCustomerIdAndSaleIdAndStatusAndReasonCodeAndReasonRef(
                entity.getCustomerId(),
                entity.getSaleId(),
                entity.getStatus(),
                entity.getReasonCode(),
                entity.getReasonRef());
    if (dbEntity != null) {
      repository.deleteById(dbEntity.getId());
    }
  }
}
