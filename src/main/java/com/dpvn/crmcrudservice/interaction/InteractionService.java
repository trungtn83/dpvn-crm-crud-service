package com.dpvn.crmcrudservice.interaction;

import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.crmcrudservice.repository.InteractionRepository;
import com.dpvn.sharedjpa.service.AbstractCrudService;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InteractionService extends AbstractCrudService<Interaction> {

  public InteractionService(InteractionRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<Interaction> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Page<Interaction> findAllInteractionsByCustomer(
      Long userId, Long customerId, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return ((InteractionRepository) repository)
        .findInteractionsByCustomer(userId, customerId, pageable);
  }

  public List<Interaction> getLastInteractionDates(Long userId, List<Long> customerIds) {
    return ((InteractionRepository) repository).findLastInteractionsDate(userId, customerIds);
  }

  public Long countReportInteractionBySeller(Long sellerId, Instant fromDate, Instant toDate) {
    return ((InteractionRepository) repository)
        .countReportInteractionsBySeller(sellerId, fromDate, toDate);
  }
}
