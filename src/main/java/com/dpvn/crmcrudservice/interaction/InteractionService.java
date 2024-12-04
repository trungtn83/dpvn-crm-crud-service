package com.dpvn.crmcrudservice.interaction;

import com.dpvn.crmcrudservice.AbstractService;
import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.crmcrudservice.repository.InteractionRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InteractionService extends AbstractService<Interaction> {

  public InteractionService(InteractionRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<Interaction> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public List<Interaction> getInteractionsByOptions(
      Long userId, Long customerId, Long campaignId, Integer visibility) {
    return ((InteractionRepository) repository)
        .findInteractionsByOptions(userId, customerId, campaignId, visibility);
  }

  public List<Interaction> getLastInteractionDates(Long userId, List<Long> customerIds) {
    return ((InteractionRepository) repository).findLastInteractionsDate(userId, customerIds);
  }
}
