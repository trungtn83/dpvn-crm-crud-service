package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.shared.repository.AbstractRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends AbstractRepository<Customer> {
  List<Customer> findByIdIn(List<Long> ids);

  String FIND_CUSTOMER_BY_MOBILE_PHONE =
      """
       SELECT DISTINCT c.*
       FROM customer c
       LEFT JOIN customer_reference cr ON c.id = cr.customer_id
       WHERE (c.mobile_phone = :mobilePhone OR ((cr.code = 'MOBILE_PHONE' AND cr.value = :mobilePhone) OR (cr.code = 'ZALO' AND cr.value = :mobilePhone)));
      """;

  @Query(value = FIND_CUSTOMER_BY_MOBILE_PHONE, nativeQuery = true)
  List<Customer> findCustomersByMobilePhone(String mobilePhone);

  Customer findFirstBySourceIdOrderByCreatedDateDesc(@Param("sourceId") Integer sourceId);

  @Query("SELECT c FROM Customer c WHERE c.active = true AND c.status IS NULL")
  Page<Customer> findByStatusForInitRelationship(Pageable pageable);

  Optional<Customer> findByMobilePhone(String mobilePhone);

  @Query(
      value =
          "SELECT count(*) FROM customer WHERE id in :ids and created_date >= :fromDate and created_date < :toDate",
      nativeQuery = true)
  Object countCustomerInIdsAndBetween(Set<Long> ids, Instant fromDate, Instant toDate);
}
