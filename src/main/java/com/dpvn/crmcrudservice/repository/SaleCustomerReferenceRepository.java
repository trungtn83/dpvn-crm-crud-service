package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomerReference;
import com.dpvn.sharedjpa.repository.AbstractRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleCustomerReferenceRepository extends AbstractRepository<SaleCustomerReference> {
  Optional<SaleCustomerReference> getBySaleIdAndCustomerIdAndStatus(
      Long saleId, Long customerId, String status);

  List<SaleCustomerReference> findAllBySaleIdAndCustomerIdInAndActiveTrueAndDeletedFalse(
      Long saleId, Collection<Long> customerIds);

  @Query(
      "select cr from SaleCustomerReference cr where cr.customerId in :customerIds AND cr.status = 'BOOM'")
  List<SaleCustomerReference> findAllBoomCustomerByIds(Collection<Long> customerIds);
}
