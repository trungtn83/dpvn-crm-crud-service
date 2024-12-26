package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomerState;
import com.dpvn.shared.repository.AbstractRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleCustomerStateRepository extends AbstractRepository<SaleCustomerState> {
  SaleCustomerState findBySaleIdAndCustomerId(Long saleId, Long customerId);

  String FIND_LATEST_SALE_CUSTOMER_STATE =
      """
          SELECT s.*
          FROM sale_customer_state s
          WHERE s.sale_id = :saleId
            AND (:customerIds IS NULL OR s.customer_id in :customerIds)
            AND s.created_date = (
                SELECT MAX(sub.created_date)
                FROM sale_customer_state sub
                WHERE sub.sale_id = s.sale_id
                  AND sub.customer_id = s.customer_id
                  AND (:customerIds IS NULL OR sub.customer_id in :customerIds)
            );
      """;

  @Query(value = FIND_LATEST_SALE_CUSTOMER_STATE, nativeQuery = true)
  List<SaleCustomerState> findLatestBySaleIdAndCustomerIds(
      @Param("saleId") Long saleId, @Param("customerIds") List<Long> customerIds);
}
