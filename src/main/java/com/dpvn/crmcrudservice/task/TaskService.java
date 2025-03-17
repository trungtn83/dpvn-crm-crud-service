package com.dpvn.crmcrudservice.task;

import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.crmcrudservice.repository.TaskCustomRepository;
import com.dpvn.crmcrudservice.repository.TaskRepository;
import com.dpvn.shared.service.AbstractCrudService;
import com.dpvn.shared.util.DateUtil;
import com.dpvn.shared.util.LocalDateUtil;
import com.dpvn.shared.util.StringUtil;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends AbstractCrudService<Task> {
  private final TaskCustomRepository taskCustomRepository;
  private final TaskRepository taskRepository;

  public TaskService(
      TaskRepository repository,
      TaskCustomRepository taskCustomRepository,
      TaskRepository taskRepository) {
    super(repository);
    this.taskCustomRepository = taskCustomRepository;
    this.taskRepository = taskRepository;
  }

  @Override
  public void sync(List<Task> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Page<Task> findTask(
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
    return taskCustomRepository.findTasks(
        userId,
        customerId,
        filterText,
        tags,
        statuses,
        progresses,
        fromDate,
        toDate,
        sorts,
        page,
        pageSize);
  }

  public List<Task> reportTasksBySeller(Long sellerId, String fromDateStr, String toDateStr) {
    Instant fromDate =
        StringUtil.isNotEmpty(fromDateStr) ? DateUtil.from(LocalDateUtil.from(fromDateStr)) : null;
    Instant toDate =
        StringUtil.isNotEmpty(toDateStr) ? DateUtil.from(LocalDateUtil.from(toDateStr)) : null;
    return taskRepository.findTasksForSellerReport(sellerId, fromDate, toDate);
  }
}
