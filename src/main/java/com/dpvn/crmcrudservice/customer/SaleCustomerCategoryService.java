package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.AbstractService;
import com.dpvn.crmcrudservice.domain.dto.SaleCustomerDto;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomerCategory;
import com.dpvn.crmcrudservice.repository.CustomerRepository;
import com.dpvn.crmcrudservice.repository.Paginator;
import com.dpvn.crmcrudservice.repository.SaleCustomerCategoryRepository;
import com.dpvn.crmcrudservice.repository.SaleCustomerCustomRepository;
import com.dpvn.crmcrudservice.repository.SaleCustomerRepository;
import com.dpvn.shared.exception.BadRequestException;
import com.dpvn.shared.util.FastMap;
import com.dpvn.shared.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SaleCustomerCategoryService extends AbstractService<SaleCustomerCategory> {
  public SaleCustomerCategoryService(
      SaleCustomerCategoryRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<SaleCustomerCategory> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public List<SaleCustomerCategory> findBySaleId(Long saleId) {
    return ((SaleCustomerCategoryRepository) repository).findBySaleId(saleId);
  }

  public List<SaleCustomerCategory> findByCode(Long saleId, String code) {
    return ((SaleCustomerCategoryRepository) repository).findBySaleIdAndCode(saleId, code);
  }

}
