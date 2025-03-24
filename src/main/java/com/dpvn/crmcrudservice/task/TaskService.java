package com.dpvn.crmcrudservice.task;

import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.crmcrudservice.repository.TaskCustomRepository;
import com.dpvn.crmcrudservice.repository.TaskRepository;
import com.dpvn.shared.service.AbstractCrudService;
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
      Long sellerId,
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
        sellerId,
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
}
