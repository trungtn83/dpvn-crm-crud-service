package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.shared.repository.AbstractRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends AbstractRepository<Interaction> {
  String FIND_INTERACTIONS_BY_OPTIONS =
      """
      SELECT i FROM Interaction i
      WHERE (:userId IS NULL OR i.interactBy = :userId)
          AND (:customerId IS NULL OR i.customerId = :customerId)
          AND (:campaignId IS NULL OR i.campaignId = :campaignId)
          AND (:visibility IS NULL OR i.visibility = :visibility)
      """;

  @Query(FIND_INTERACTIONS_BY_OPTIONS)
  List<Interaction> findInteractionsByOptions(
      @Param("userId") Long userId,
      @Param("customerId") Long customerId,
      @Param("campaignId") Long campaignId,
      @Param("visibility") Integer visibility);

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

  @Query(
      value =
          "select count(*) from interaction where interact_by = :saleId and created_date >= :fromDate and created_date < :toDate",
      nativeQuery = true)
  Long countReportInteractionsBySeller(Long saleId, Instant fromDate, Instant toDate);

  String REPORT_INTERACTIONS_BY_SELLERS =
      """
        select i.id, i.interact_by , i.customer_id, c.customer_name, c.mobile_phone, i.title, i."content", i.visibility, i.created_date
        from interaction i
        left join customer c on i.customer_id  = c.id
        where i.active = true and i.deleted is not true
            and i.interact_by in :sellerIds
            and i.type_id <> -1
            and i.created_date >= :fromDate and i.created_date < :toDate
      """;

  @Query(value = REPORT_INTERACTIONS_BY_SELLERS, nativeQuery = true)
  List<Object[]> reportInteractionsBySellers(
      List<Long> sellerIds, Instant fromDate, Instant toDate);
}
