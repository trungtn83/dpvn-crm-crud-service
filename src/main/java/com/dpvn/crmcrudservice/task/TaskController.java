package com.dpvn.crmcrudservice.task;

import com.dpvn.crmcrudservice.domain.dto.TaskDto;
import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.crmcrudservice.domain.mapper.TaskMapper;
import com.dpvn.sharedcore.util.DateUtil;
import com.dpvn.sharedcore.util.FastMap;
import com.dpvn.sharedcore.util.LocalDateUtil;
import com.dpvn.sharedcore.util.StringUtil;
import com.dpvn.sharedjpa.controller.AbstractCrudController;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController extends AbstractCrudController<Task, TaskDto> {
  public TaskController(TaskMapper mapper, TaskService service) {
    super(mapper, service);
  }

  @PostMapping("/find-by-options")
  public FastMap findTasks(@RequestBody FastMap body) {
    Long userId = body.getLong("userId");
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
    return FastMap.create()
        .add("rows", mapper.toDtoList(taskPage.getContent()))
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
