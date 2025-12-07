package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.sharedjpa.repository.AbstractRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends AbstractRepository<Interaction> {
  String FIND_INTERACTIONS_BY_OPTIONS =
      """
        SELECT i FROM Interaction i
        WHERE i.customerId = :customerId AND (i.visibility = 0 OR (:userId IS NULL OR i.interactBy = :userId))
        ORDER BY i.createdDate DESC
      """;

  @Query(FIND_INTERACTIONS_BY_OPTIONS)
  Page<Interaction> findInteractionsByCustomer(
      @Param("userId") Long userId, @Param("customerId") Long customerId, Pageable pageable);

  String FIND_LAST_INTERACTIONS_DATE =
      """
          SELECT DISTINCT ON (i.customer_id) i.*
          FROM interaction i
          WHERE (:userId IS NULL OR i.interact_by = :userId)
           AND (:customerIds IS NULL OR i.customer_id IN :customerIds)
          ORDER BY i.customer_id, i.created_date DESC;
      """;

  @Query(value = FIND_LAST_INTERACTIONS_DATE, nativeQuery = true)
  List<Interaction> findLastInteractionsDate(
      @Param("userId") Long userId, @Param("customerIds") List<Long> customerIds);

  String REPORT_INTERACTIONS_BY_SELLERS =
      """
        select i.id, i.interact_by , i.customer_id, c.customer_name, c.mobile_phone, i.title, i."content", i.visibility, i.created_date
        from interaction i
        left join customer c on i.customer_id  = c.id
        where i.active = true and i.deleted = false
            and i.interact_by in :saleIds
            and i.type_id <> -1
            and i.created_date >= :fromDate and i.created_date < :toDate
      """;

  @Query(value = REPORT_INTERACTIONS_BY_SELLERS, nativeQuery = true)
  List<Object[]> reportInteractionsBySellers(List<Long> saleIds, Instant fromDate, Instant toDate);
}
