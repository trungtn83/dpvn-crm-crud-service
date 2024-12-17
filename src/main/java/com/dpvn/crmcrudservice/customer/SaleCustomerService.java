package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.dto.SaleCustomerDto;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.repository.CustomerRepository;
import com.dpvn.crmcrudservice.repository.SaleCustomerCustomRepository;
import com.dpvn.crmcrudservice.repository.SaleCustomerRepository;
import com.dpvn.shared.exception.BadRequestException;
import com.dpvn.shared.service.AbstractService;
import com.dpvn.shared.util.ListUtil;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SaleCustomerService extends AbstractService<SaleCustomer> {
  private final SaleCustomerCustomRepository saleCustomerCustomRepository;
  private final CustomerRepository customerRepository;

  public SaleCustomerService(
      SaleCustomerRepository repository,
      SaleCustomerCustomRepository saleCustomerCustomRepository,
      CustomerRepository customerRepository) {
    super(repository);
    this.saleCustomerCustomRepository = saleCustomerCustomRepository;
    this.customerRepository = customerRepository;
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
      saleCustomers.forEach(this::delete);
    }
  }

  @Override
  public SaleCustomer upsert(SaleCustomer entity) {
    Long id = entity.getId();
    Customer customer =
        customerRepository
            .findById(entity.getCustomer().getId())
            .orElseThrow(() -> new BadRequestException("Customer not found"));
    entity.setCustomer(customer);
    if (id == null) {
      return save(entity);
    }
    return update(id, entity);
  }
}
