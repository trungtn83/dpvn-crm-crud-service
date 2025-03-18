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

  //  List<SaleCustomer> findBySaleIdAndCreatedDateGreaterThanEqualAndCreatedDateLessThan(
  //      Long saleId, Instant fromDate, Instant toDate);

  @Query(
      value =
          "select customer_id from sale_customer where sale_id = :saleId and created_date >= :fromDate and created_date < :toDate",
      nativeQuery = true)
  List<Object[]> findBySaleIdAndCreatedDateGreaterThanEqualAndCreatedDateLessThan(
      Long saleId, Instant fromDate, Instant toDate);
}
