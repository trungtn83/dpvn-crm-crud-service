package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomerState;
import com.dpvn.crmcrudservice.repository.SaleCustomerStateRepository;
import com.dpvn.shared.service.AbstractCrudService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SaleCustomerStateService extends AbstractCrudService<SaleCustomerState> {

  public SaleCustomerStateService(SaleCustomerStateRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<SaleCustomerState> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public SaleCustomerState getSaleCustomerStateBySale(Long saleId, Long customerId) {
    return ((SaleCustomerStateRepository) repository).findBySaleIdAndCustomerId(saleId, customerId);
  }

  public List<SaleCustomerState> findLatestBySaleIdAndCustomerIds(
      Long saleId, List<Long> customerIds) {
    return ((SaleCustomerStateRepository) repository)
        .findLatestBySaleIdAndCustomerIds(saleId, customerIds);
  }
}
