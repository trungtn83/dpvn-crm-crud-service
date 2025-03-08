package com.dpvn.crmcrudservice.task;

import com.dpvn.crmcrudservice.domain.dto.TaskDto;
import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.shared.controller.AbstractCrudController;
import java.util.List;

import com.dpvn.shared.util.FastMap;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController extends AbstractCrudController<Task, TaskDto> {

  public TaskController(TaskService service) {
    super(service);
  }

  @PostMapping("/find-by-options")
  public FastMap findTasks(@RequestBody FastMap body) {
    Long userId = body.getLong("userId");
    Long customerId = body.getLong("customerId");
    String filterText = body.getString("filterText");
    List<String> tags = body.getList("tags");
    List<String> statuses = body.getList("statuses");
    List<Integer> progress = body.getListClass("progress", Integer.class);
    List<String> sorts = body.getList("sorts");

    Integer page = body.getInt("page");
    Integer pageSize = body.getInt("pageSize");

    Page<Task> taskPage = ((TaskService) service).findTask(userId, customerId, filterText, tags, statuses, progress, sorts, page, pageSize);
    return FastMap.create()
        .add("rows", taskPage.getContent().stream().map(Task::toDto))
        .add("total", taskPage.getTotalElements())
        .add("pageSize", taskPage.getSize())
        .add("page", taskPage.getNumber());
  }
}
