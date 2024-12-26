package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.dto.SaleCustomerDto;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.repository.SaleCustomerCustomRepository;
import com.dpvn.crmcrudservice.repository.SaleCustomerRepository;
import com.dpvn.shared.service.AbstractCrudService;
import com.dpvn.shared.util.ListUtil;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SaleCustomerService extends AbstractCrudService<SaleCustomer> {
  private final SaleCustomerCustomRepository saleCustomerCustomRepository;

  public SaleCustomerService(
      SaleCustomerRepository repository,
      SaleCustomerCustomRepository saleCustomerCustomRepository) {
    super(repository);
    this.saleCustomerCustomRepository = saleCustomerCustomRepository;
  }

  @Override
  public void sync(List<SaleCustomer> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public List<SaleCustomer> findSaleCustomersByOptions(
      Long saleId,
      List<Long> customerIds,
      Integer relationshipType,
      List<Integer> reasonIds,
      String reasonRef) {
    return saleCustomerCustomRepository.findSaleCustomersByOptions(
        saleId, customerIds, relationshipType, reasonIds, reasonRef);
  }

  public void removeSaleCustomerByOptions(SaleCustomerDto dto) {
    List<SaleCustomer> saleCustomers =
        saleCustomerCustomRepository.findSaleCustomersByOptions(
            dto.getSaleId(),
            List.of(dto.getCustomerId()),
            dto.getRelationshipType(),
            List.of(dto.getReasonId()),
            dto.getReasonRef());
    if (ListUtil.isNotEmpty(saleCustomers)) {
      saleCustomers.forEach(saleCustomer -> delete(saleCustomer.getId()));
    }
  }
}
