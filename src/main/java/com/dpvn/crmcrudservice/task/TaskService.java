package com.dpvn.crmcrudservice.task;

import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.crmcrudservice.repository.TaskRepository;
import com.dpvn.shared.service.AbstractCrudService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends AbstractCrudService<Task> {
  public TaskService(TaskRepository repository) {
    super(repository);
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
}
