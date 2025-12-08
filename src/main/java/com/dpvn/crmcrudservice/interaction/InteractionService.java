package com.dpvn.crmcrudservice.interaction;

import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.crmcrudservice.repository.InteractionRepository;
import com.dpvn.sharedcore.util.DateUtil;
import com.dpvn.sharedcore.util.FastMap;
import com.dpvn.sharedcore.util.LocalDateUtil;
import com.dpvn.sharedjpa.service.AbstractCrudService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

  public Map<Long, List<FastMap>> reportInteractionsBySeller(
      List<Long> saleIds, String fromDateStr, String toDateStr) {
    Instant fromDate = DateUtil.from(LocalDateUtil.from(fromDateStr));
    Instant toDate = DateUtil.from(LocalDateUtil.from(toDateStr)).plus(1, ChronoUnit.DAYS);

    List<Object[]> os =
        ((InteractionRepository) repository).reportInteractionsBySellers(saleIds, fromDate, toDate);
    List<FastMap> interactionFms = os.stream().map(this::transformToFastMap).toList();
    return interactionFms.stream().collect(Collectors.groupingBy(fm -> fm.getLong("interactBy")));
  }

  private FastMap transformToFastMap(Object[] os) {
    return FastMap.create()
        .add("id", os[0])
        .add("interactBy", os[1])
        .add("customerId", os[2])
        .add("customerName", os[3])
        .add("mobilePhone", os[4])
        .add("title", os[5])
        .add("content", os[6])
        .add("visibility", os[7])
        .add("createdDate", os[8]);
  }
}
