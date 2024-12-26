package com.dpvn.crmcrudservice.interaction;

import com.dpvn.crmcrudservice.domain.dto.InteractionDto;
import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.shared.controller.AbstractCrudController;
import com.dpvn.shared.util.FastMap;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interaction")
public class InteractionController extends AbstractCrudController<Interaction, InteractionDto> {

  public InteractionController(InteractionService service) {
    super(service);
  }

  @GetMapping("/find-by-options")
  public List<InteractionDto> getAllInteractions(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) Long customerId,
      @RequestParam(required = false) Long campaignId,
      @RequestParam(required = false) Integer visibility) {
    return ((InteractionService) service)
        .getInteractionsByOptions(userId, customerId, campaignId, visibility).stream()
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
