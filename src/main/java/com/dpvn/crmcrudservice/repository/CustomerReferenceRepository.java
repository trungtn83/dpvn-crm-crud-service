package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.CustomerReference;
import com.dpvn.sharedjpa.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerReferenceRepository extends AbstractRepository<CustomerReference> {
  @Query(
      "select cr from CustomerReference cr where cr.code like 'PAPER_%' AND cr.modifiedDate is null")
  Page<CustomerReference> findAllCustomerReferencesWithPaperCode(Pageable pageable);

  void deleteCustomerReferenceByValueIsNull();
}
