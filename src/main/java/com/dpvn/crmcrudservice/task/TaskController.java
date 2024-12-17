package com.dpvn.crmcrudservice.task;

import com.dpvn.crmcrudservice.domain.dto.TaskDto;
import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.shared.controller.AbstractController;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController extends AbstractController<Task, TaskDto> {

  public TaskController(TaskService service) {
    super(service);
  }

  @GetMapping("/find-by-options")
  public List<TaskDto> getAllTasks(
      @RequestParam Long userId,
      @RequestParam(required = false) Long customerId,
      @RequestParam(required = false) Long campaignId,
      @RequestParam(required = false) Long kpiId,
      @RequestParam(required = false) Long otherId) {
    return ((TaskService) service)
        .getTasksByOptions(userId, customerId, campaignId, kpiId, otherId).stream()
            .map(Task::toDto)
            .toList();
  }
}
