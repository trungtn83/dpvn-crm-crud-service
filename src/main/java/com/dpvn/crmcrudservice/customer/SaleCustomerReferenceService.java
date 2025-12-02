package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomerReference;
import com.dpvn.crmcrudservice.repository.SaleCustomerReferenceRepository;
import com.dpvn.sharedjpa.service.AbstractCrudService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SaleCustomerReferenceService extends AbstractCrudService<SaleCustomerReference> {
  public SaleCustomerReferenceService(SaleCustomerReferenceRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<SaleCustomerReference> list) {}

  public void upsertSaleCustomerReference(SaleCustomerReference entity) {
    SaleCustomerReference dbEntity =
        ((SaleCustomerReferenceRepository) repository)
            .getBySaleIdAndCustomerIdAndStatus(
                entity.getSaleId(), entity.getCustomerId(), entity.getStatus())
            .orElse(null);
    if (dbEntity == null) {
      repository.save(entity);
    } else {
      dbEntity.setActive(entity.getActive());
      dbEntity.setDeleted(entity.getDeleted());
      repository.save(dbEntity);
    }
  }
}
