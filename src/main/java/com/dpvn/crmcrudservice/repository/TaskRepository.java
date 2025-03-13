package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.shared.repository.AbstractRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends AbstractRepository<Task> {
  String FIND_TASKS_FOR_SELLER_REPORT =
      """
        select * from task t
        where t.user_id = :saleId
          and t.progress = 100
          and (:fromDate is null or t.modified_date between >= :fromDate) and (:toDate is null or t.modified_date < :toDate)
      """;

  @Query(value = FIND_TASKS_FOR_SELLER_REPORT, nativeQuery = true)
  List<Task> findTasksForSellerReport(
      @Param("saleId") Long userId,
      @Param("fromDate") Instant fromDate,
      @Param("toDate") Instant toDate);

  List<Task> findByIdIn(List<Long> ids);
}
