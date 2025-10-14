package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.CustomerReference;
import com.dpvn.shared.repository.AbstractRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerReferenceRepository extends AbstractRepository<CustomerReference> {
  @Query(
      value =
          "select * from customer_reference cr where cr.code like 'PAPER_%' and cr.reference is not null and cr.value is null",
      nativeQuery = true)
  List<CustomerReference> findErrorCustomerReferences();
}
