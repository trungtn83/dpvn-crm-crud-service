package com.dpvn.crmcrudservice.interaction;

import com.dpvn.crmcrudservice.domain.dto.InteractionDto;
import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.shared.controller.AbstractCrudController;
import com.dpvn.shared.util.DateUtil;
import com.dpvn.shared.util.FastMap;
import com.dpvn.shared.util.LocalDateUtil;
import java.time.Instant;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interaction")
public class InteractionController extends AbstractCrudController<Interaction, InteractionDto> {

  public InteractionController(InteractionService service) {
    super(service);
  }

  @PostMapping("/find-by-options")
  public List<InteractionDto> findAllInteractions(@RequestBody FastMap body) {
    Long userId = body.getLong("userId");
    Long customerId = body.getLong("customerId");
    Long campaignId = body.getLong("campaignId");
    Integer visibility = body.getInt("visibility");
    String fromDateStr = body.getString("fromDate");
    String toDateStr = body.getString("toDate");
    Instant fromDate = DateUtil.from(LocalDateUtil.from(fromDateStr));
    Instant toDate = DateUtil.from(LocalDateUtil.from(toDateStr));
    return ((InteractionService) service)
            .getInteractionsByOptions(userId, customerId, campaignId, visibility, fromDate, toDate)
            .stream()
            .map(Interaction::toDto)
            .toList();
  }

  @PostMapping("/find-last-interactions-date")
  public List<InteractionDto> getLastInteractionDates(@RequestBody FastMap body) {
    Long userId = body.getLong("userId");
    List<Long> customerIds = body.getList("customerIds");
    return ((InteractionService) service)
        .getLastInteractionDates(userId, customerIds).stream().map(Interaction::toDto).toList();
  }
}
