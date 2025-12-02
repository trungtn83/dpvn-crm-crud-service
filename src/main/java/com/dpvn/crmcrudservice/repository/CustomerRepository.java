package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.sharedjpa.repository.AbstractRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends AbstractRepository<Customer> {
  /**
   * CREATE INDEX idx_customer_mobile ON customer(mobile_phone);
   * CREATE INDEX idx_customer_ref_mobile ON customer_reference(code, value);
   */
  String FIND_CUSTOMER_BY_MOBILE_PHONE =
      """
          SELECT DISTINCT c.*
          FROM customer c
          LEFT JOIN customer_reference cr
              ON c.id = cr.customer_id AND cr.code IN ('MOBILE_PHONE', 'ZALO')
          WHERE c.mobile_phone IN :mobilePhones OR cr.value IN :mobilePhones
      """;

  @Query(value = FIND_CUSTOMER_BY_MOBILE_PHONE, nativeQuery = true)
  List<Customer> findAllCustomersByMobilePhoneIn(List<String> mobilePhones);
}
