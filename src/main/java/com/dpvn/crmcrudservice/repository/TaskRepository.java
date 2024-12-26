package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.shared.repository.AbstractRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends AbstractRepository<Task> {
  String FIND_TASKS_BY_OPTIONS =
      """
      SELECT t FROM Task t
      WHERE (:userId IS NULL OR t.userId = :userId)
          AND (:customerId IS NULL OR t.customerId = :customerId)
          AND (:campaignId IS NULL OR t.campaignId = :campaignId)
          AND (:kpiId IS NULL OR t.kpiId = :kpiId)
          AND (:otherId IS NULL OR t.otherId = :otherId)
      """;

  @Query(FIND_TASKS_BY_OPTIONS)
  List<Task> findTasksByOptions(
      @Param("userId") Long userId,
      @Param("customerId") Long customerId,
      @Param("campaignId") Long campaignId,
      @Param("kpiId") Long kpiId,
      @Param("otherId") Long otherId);
}
