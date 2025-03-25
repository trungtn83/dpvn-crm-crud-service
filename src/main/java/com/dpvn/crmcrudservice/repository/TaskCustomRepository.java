package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.shared.domain.constant.Globals;
import com.dpvn.shared.service.AbstractService;
import com.dpvn.shared.util.ListUtil;
import com.dpvn.shared.util.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class TaskCustomRepository extends AbstractService {
  private final TaskRepository taskRepository;
  @PersistenceContext private EntityManager entityManager;

  public TaskCustomRepository(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public Page<Task> findTasks(
      Long userId,
      Long customerId,
      String filterText,
      List<String> tags,
      List<String> statuses,
      List<Integer> progresses,
      Instant fromDate,
      Instant toDate,
      List<String> sorts,
      Integer page,
      Integer pageSize) {
    String SELECT = "SELECT id";
    String SELECT_COUNT = "SELECT count(*)";
    String FROM = generateFrom();
    String WHERE =
        generateWhere(userId, customerId, filterText, tags, statuses, progresses, fromDate, toDate);
    String SORT = generateSort(sorts);

    List<Task> results =
        getTaskResults(
            String.format("%s %s %s %s", SELECT, FROM, WHERE, SORT),
            userId,
            customerId,
            filterText,
            tags,
            statuses,
            progresses,
            fromDate,
            toDate,
            page,
            pageSize);
    Long total =
        getTaskTotal(
            String.format("%s %s %s", SELECT_COUNT, FROM, WHERE),
            userId,
            customerId,
            filterText,
            tags,
            statuses,
            progresses,
            fromDate,
            toDate);

    if (page == null || pageSize == null) {
      page = 0;
      pageSize = ListUtil.isEmpty(results) ? Globals.Paging.PAGE_SIZE : results.size();
    }
    PageRequest pageRequest = PageRequest.of(page, pageSize);
    return new PageImpl<>(results, pageRequest, total);
  }

  private List<Task> getTaskResults(
      String sql,
      Long userId,
      Long customerId,
      String filterText,
      List<String> tags,
      List<String> statuses,
      List<Integer> progresses,
      Instant fromDate,
      Instant toDate,
      Integer page,
      Integer pageSize) {
    Query query = entityManager.createNativeQuery(sql);
    if (userId != null) {
      query.setParameter("userId", userId);
    }
    if (customerId != null) {
      query.setParameter("customerId", customerId);
    }
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    if (ListUtil.isNotEmpty(tags)) {
      query.setParameter("tags", tags);
    }
    if (ListUtil.isNotEmpty(statuses)) {
      query.setParameter("statuses", statuses);
    }
    if (ListUtil.isNotEmpty(progresses)) {
      query.setParameter("progresses", progresses);
    }
    if (fromDate != null) {
      query.setParameter("fromDate", fromDate);
    }
    if (toDate != null) {
      query.setParameter("toDate", toDate);
    }

    if (page != null && pageSize != null) {
      query.setFirstResult(page * pageSize);
      query.setMaxResults(pageSize);
    }

    List<Long> os = query.getResultList();
    return taskRepository.findByIdIn(os).stream()
        .sorted(Comparator.comparing((t -> os.indexOf(t.getId()))))
        .toList();
  }

  private Long getTaskTotal(
      String sql,
      Long userId,
      Long customerId,
      String filterText,
      List<String> tags,
      List<String> statuses,
      List<Integer> progresses,
      Instant fromDate,
      Instant toDate) {
    Query query = entityManager.createNativeQuery(sql);
    if (userId != null) {
      query.setParameter("userId", userId);
    }
    if (customerId != null) {
      query.setParameter("customerId", customerId);
    }
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    if (ListUtil.isNotEmpty(tags)) {
      query.setParameter("tags", tags);
    }
    if (ListUtil.isNotEmpty(statuses)) {
      query.setParameter("statuses", statuses);
    }
    if (ListUtil.isNotEmpty(progresses)) {
      query.setParameter("progresses", progresses);
    }
    if (fromDate != null) {
      query.setParameter("fromDate", fromDate);
    }
    if (toDate != null) {
      query.setParameter("toDate", toDate);
    }
    return ((Number) query.getSingleResult()).longValue();
  }

  private String generateFrom() {
    return "FROM task t";
  }

  private String generateWhere(
      Long userId,
      Long customerId,
      String filterText,
      List<String> tags,
      List<String> statuses,
      List<Integer> progresses,
      Instant fromDate,
      Instant toDate) {
    String WHERE = "WHERE t.active = TRUE AND t.deleted IS NOT TRUE";
    if (userId != null) {
      WHERE += " AND t.user_id = :userId";
    }
    if (customerId != null) {
      WHERE += " AND t.customer_id = :customerId";
    }
    if (StringUtil.isNotEmpty(filterText)) {
      WHERE +=
          " AND (t.title ILIKE '%' || :filterText || '%' OR t.name ILIKE '%' || :filterText || '%' OR t.content ILIKE '%' || :filterText || '%')";
    }
    if (ListUtil.isNotEmpty(tags)) {
      LOGGER.info("Will be implemented in future");
    }
    if (ListUtil.isNotEmpty(statuses)) {
      WHERE += " AND t.status IN :statuses";
    }
    if (ListUtil.isNotEmpty(progresses)) {
      WHERE += " AND t.progress IN :progresses";
    }
    if (fromDate != null) {
      WHERE += " AND t.modified_date >= :fromDate";
    }
    if (toDate != null) {
      WHERE += " AND t.modified_date < :toDate";
    }
    return WHERE;
  }

  private String generateSort(List<String> sorts) {
    String ORDER_BY = "ORDER BY ";
    if (ListUtil.isNotEmpty(sorts)) {
      ORDER_BY += StringUtil.join(sorts, ",");
    } else {
      ORDER_BY += " t.created_date DESC";
    }
    return ORDER_BY;
  }
}
