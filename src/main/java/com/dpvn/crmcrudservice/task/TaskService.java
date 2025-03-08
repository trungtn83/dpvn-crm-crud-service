package com.dpvn.crmcrudservice.task;

import com.dpvn.crmcrudservice.domain.dto.TaskDto;
import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.crmcrudservice.repository.TaskCustomRepository;
import com.dpvn.crmcrudservice.repository.TaskRepository;
import com.dpvn.shared.service.AbstractCrudService;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends AbstractCrudService<Task> {
  private final TaskCustomRepository taskCustomRepository;

  public TaskService(TaskRepository repository, TaskCustomRepository taskCustomRepository) {
    super(repository);
    this.taskCustomRepository = taskCustomRepository;
  }

  @Override
  public void sync(List<Task> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public List<Task> getTasksByOptions(
      Long userId, Long customerId, Long campaignId, Long kpiId, Long otherId) {
    return ((TaskRepository) repository)
        .findTasksByOptions(userId, customerId, campaignId, kpiId, otherId);
  }

  public Page<Task> findTask(Long userId, Long customerId, String filterText, List<String> tags, List<String> statuses, List<Integer> progresses, List<String> sorts, Integer page, Integer pageSize) {
    return taskCustomRepository.findTasks(userId, customerId, filterText, tags, statuses, progresses, sorts, page, pageSize);
  }
}
