// package com.dpvn.crmcrudservice.customerold;
//
// import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
// import com.dpvn.crmcrudservice.repository.SaleCustomerCustomRepository;
// import com.dpvn.crmcrudservice.repository.SaleCustomerRepository;
// import com.dpvn.sharedjpa.service.AbstractCrudService;
// import com.dpvn.sharedcore.util.ListUtil;
// import java.util.List;
// import org.springframework.stereotype.Service;
//
// @Service
// public class SaleCustomerService extends AbstractCrudService<SaleCustomer> {
//  private final SaleCustomerCustomRepository saleCustomerCustomRepository;
//
//  public SaleCustomerService(
//      SaleCustomerRepository repository,
//      SaleCustomerCustomRepository saleCustomerCustomRepository) {
//    super(repository);
//    this.saleCustomerCustomRepository = saleCustomerCustomRepository;
//  }
//
//  @Override
//  public void sync(List<SaleCustomer> entities) {
//    throw new UnsupportedOperationException("Not supported yet.");
//  }
//
//  public List<SaleCustomer> findSaleCustomersByOptions(
//      Long saleId,
//      List<Long> customerIds,
//      Integer relationshipType,
//      List<Integer> reasonIds,
//      String reasonRef) {
//    return saleCustomerCustomRepository.findSaleCustomersByOptions(
//        saleId, customerIds, relationshipType, reasonIds, reasonRef);
//  }
//
//  public void removeSaleCustomerByOptions(SaleCustomer entity) {
//    List<SaleCustomer> saleCustomers =
//        saleCustomerCustomRepository.findSaleCustomersByOptions(
//            entity.getSaleId(),
//            entity.getCustomer() == null ? List.of() : List.of(entity.getCustomer().getId()),
//            entity.getRelationshipType(),
//            entity.getReasonId() == null ? List.of() : List.of(entity.getReasonId()),
//            entity.getReasonRef());
//    if (ListUtil.isNotEmpty(saleCustomers)) {
//      saleCustomers.forEach(saleCustomer -> delete(saleCustomer.getId()));
//    }
//  }
// }
