package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.shared.repository.AbstractRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleCustomerRepository extends AbstractRepository<SaleCustomer> {
  SaleCustomer findBySaleIdAndCustomerIdAndActiveAndDeleted(
      Long saleId, Long customerId, Boolean status, Boolean deleted);

  @Query(
      value =
          "select customer_id from sale_customer where sale_id = :saleId and created_date >= :fromDate and created_date < :toDate",
      nativeQuery = true)
  List<Object[]> findBySaleIdAndCreatedDateGreaterThanEqualAndCreatedDateLessThan(
      Long saleId, Instant fromDate, Instant toDate);

  /*
    Only count customer if has mobile phone, other is trash
   */
  String REPORT_CUSTOMERS_BY_SELLERS =
      """
         select distinct on (sc.customer_id) sc.customer_id, sc.sale_id, sc.reason_id, sc.created_date, c.customer_name, c.customer_code, c.mobile_phone
         from sale_customer sc
         left join customer c on sc.customer_id = c.id
         where sc.active = true and sc.deleted is not true
            and sc.sale_id in :sellerIds
                and sc.created_date >= :fromDate and sc.created_date < :toDate
                and sc.reason_id in (70,71,72)
                and c.mobile_phone is not null
      """;

  @Query(value = REPORT_CUSTOMERS_BY_SELLERS, nativeQuery = true)
  List<Object[]> reportCustomersBySellers(List<Long> sellerIds, Instant fromDate, Instant toDate);
}
