package com.dpvn.crmcrudservice.interaction;

import com.dpvn.crmcrudservice.domain.dto.InteractionDto;
import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.crmcrudservice.domain.mapper.InteractionMapper;
import com.dpvn.sharedcore.domain.constant.Globals;
import com.dpvn.sharedcore.domain.dto.PagingResponse;
import com.dpvn.sharedcore.util.DateUtil;
import com.dpvn.sharedcore.util.FastMap;
import com.dpvn.sharedcore.util.LocalDateUtil;
import com.dpvn.sharedjpa.controller.AbstractCrudController;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/interaction")
public class InteractionController extends AbstractCrudController<Interaction, InteractionDto> {

  public InteractionController(InteractionMapper mapper, InteractionService service) {
    super(mapper, service);
  }

  @PostMapping("/find-by-customer")
  public PagingResponse<InteractionDto> findAllInteractionsByCustomer(@RequestBody FastMap body) {
    int page = body.getInt(0, "page");
    int pageSize = body.getInt(Globals.Paging.PAGE_SIZE, "pageSize");
    Long userId = body.getLong("userId");
    Long customerId = body.getLong("customerId");
    Page<Interaction> pageInteraction =
        ((InteractionService) service)
            .findAllInteractionsByCustomer(userId, customerId, page, pageSize);
    return new PagingResponse<>(
        page,
        pageSize,
        pageInteraction.getTotalElements(),
        mapper.toDtoList(pageInteraction.getContent()));
  }

  @PostMapping("/find-last-interactions-date")
  public List<InteractionDto> getLastInteractionDates(@RequestBody FastMap body) {
    Long userId = body.getLong("userId");
    List<Long> customerIds = body.getList("customerIds");
    List<Interaction> interactions =
        ((InteractionService) service).getLastInteractionDates(userId, customerIds);
    return mapper.toDtoList(interactions);
  }

  @PostMapping("/report-by-seller")
  public Long countReportInteractionBySeller(@RequestBody FastMap body) {
    Long userId = body.getLong("userId");
    String fromDateStr = body.getString("fromDate");
    String toDateStr = body.getString("toDate");
    Instant fromDate = DateUtil.from(LocalDateUtil.from(fromDateStr));
    Instant toDate = DateUtil.from(LocalDateUtil.from(toDateStr).plusDays(1));
    return ((InteractionService) service).countReportInteractionBySeller(userId, fromDate, toDate);
  }
}
