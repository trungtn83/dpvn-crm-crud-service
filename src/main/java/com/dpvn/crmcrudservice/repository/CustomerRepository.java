package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.sharedjpa.repository.AbstractRepository;
import java.time.Instant;
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

  /*
  Only count customer if has mobile phone, other is trash
 */
  String REPORT_CUSTOMERS_BY_SELLERS =
      """
         select distinct on (sc.customer_id) sc.customer_id, sc.sale_id, sc.reason_code, sc.created_date, c.customer_name, c.customer_code, c.mobile_phone
         from sale_customer sc
         left join customer c on sc.customer_id = c.id
         where c.active = true and c.deleted = false and sc.active = true and sc.deleted = false
            and sc.sale_id in :sellerIds
                and sc.created_date >= :fromDate and sc.created_date < :toDate
               and sc.status = 'GOLD' and sc.reason_code in ('GOLDMINE', 'SANDBANK', 'DISCOVERY')
                and c.mobile_phone is not null
      """;

  @Query(value = REPORT_CUSTOMERS_BY_SELLERS, nativeQuery = true)
  List<Object[]> reportCustomersBySellersInRange(
      List<Long> sellerIds, Instant fromDate, Instant toDate);
}
