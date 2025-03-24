package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.shared.repository.AbstractRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends AbstractRepository<Task> {
  String FIND_TASKS_FOR_SALE_REPORT =
      """
            select t.id, t.user_id, t.name, t.title, t.content, t.from_date, t.to_date, t.modified_date from task t
            where t.user_id in :sellerIds
              and t.progress = 100
              and t.modified_date >= :fromDate and t.modified_date < :toDate
          """;

  @Query(value = FIND_TASKS_FOR_SALE_REPORT, nativeQuery = true)
  List<Object[]> reportTasksBySellers(List<Long> sellerIds, Instant fromDate, Instant toDate);

  List<Task> findByIdIn(List<Long> ids);
}
