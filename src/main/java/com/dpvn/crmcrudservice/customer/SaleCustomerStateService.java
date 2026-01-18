package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomerState;
import com.dpvn.crmcrudservice.repository.SaleCustomerStateRepository;
import com.dpvn.sharedjpa.service.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleCustomerStateService extends AbstractCrudService<SaleCustomerState> {
  private final SaleCustomerStateRepository saleCustomerStateRepository;

  public SaleCustomerStateService(SaleCustomerStateRepository repository, SaleCustomerStateRepository saleCustomerStateRepository) {
    super(repository);
    this.saleCustomerStateRepository = saleCustomerStateRepository;
  }

  @Override
  public void sync(List<SaleCustomerState> list) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void upsert(SaleCustomerState entity) {
    SaleCustomerState dbSaleCustomerState = saleCustomerStateRepository.getBySaleIdAndCustomerId(entity.getSaleId(), entity.getCustomerId());
    if (dbSaleCustomerState != null) {
      dbSaleCustomerState.setCustomerCategoryId(entity.getCustomerCategoryId());
      dbSaleCustomerState.setCharacteristic(entity.getCharacteristic());
      dbSaleCustomerState.setFeeShip(entity.getFeeShip());
      dbSaleCustomerState.setPriceMakeUp(entity.getPriceMakeUp());
      dbSaleCustomerState.setPreferProducts(entity.getPreferProducts());
      dbSaleCustomerState.setOther(entity.getOther());
      dbSaleCustomerState.setNote(entity.getNote());
      save(dbSaleCustomerState);
    } else {
      save(entity);
    }
  }
}
