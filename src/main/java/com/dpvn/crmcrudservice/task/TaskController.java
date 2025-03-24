package com.dpvn.crmcrudservice.task;

import com.dpvn.crmcrudservice.domain.dto.TaskDto;
import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.shared.controller.AbstractCrudController;
import com.dpvn.shared.util.DateUtil;
import com.dpvn.shared.util.FastMap;
import com.dpvn.shared.util.LocalDateUtil;
import com.dpvn.shared.util.StringUtil;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController extends AbstractCrudController<Task, TaskDto> {

  private final TaskService taskService;

  public TaskController(TaskService service, TaskService taskService) {
    super(service);
    this.taskService = taskService;
  }

  @PostMapping("/find-by-options")
  public FastMap findTasks(@RequestBody FastMap body) {
    Long sellerId = body.getLong("sellerId");
    Long customerId = body.getLong("customerId");
    String filterText = body.getString("filterText");
    List<String> tags = body.getList("tags");
    List<String> statuses = body.getList("statuses");
    List<Integer> progresses = body.getListClass("progresses", Integer.class);
    String fromDateStr = body.getString("fromDate");
    String toDateStr = body.getString("toDate");
    List<String> sorts = body.getList("sorts");

    Integer page = body.getInt("page");
    Integer pageSize = body.getInt("pageSize");

    Instant fromDate = toInstantFromLocalString(fromDateStr, 0L);
    Instant toDate = toInstantFromLocalString(toDateStr, 1L);
    Page<Task> taskPage =
        ((TaskService) service)
            .findTask(
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
    return FastMap.create()
        .add("rows", taskPage.getContent().stream().map(Task::toDto))
        .add("total", taskPage.getTotalElements())
        .add("pageSize", taskPage.getSize())
        .add("page", taskPage.getNumber());
  }

  private Instant toInstantFromLocalString(String localDateStr, Long days) {
    if (StringUtil.isEmpty(localDateStr)) {
      return null;
    }
    try {
      LocalDate localDate = LocalDateUtil.from(localDateStr);
      return localDate.atStartOfDay(DateUtil.LOCAL_ZONE_ID).plusDays(days).toInstant();
    } catch (Exception e) {
      return null;
    }
  }
}
