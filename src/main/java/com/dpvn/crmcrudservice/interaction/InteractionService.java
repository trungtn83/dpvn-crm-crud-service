package com.dpvn.crmcrudservice.interaction;

import com.dpvn.crmcrudservice.customer.SaleCustomerService;
import com.dpvn.crmcrudservice.domain.constant.RelationshipType;
import com.dpvn.crmcrudservice.domain.constant.SaleCustomers;
import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.repository.InteractionRepository;
import com.dpvn.shared.domain.constant.Globals;
import com.dpvn.shared.service.AbstractCrudService;
import com.dpvn.shared.util.DateUtil;
import com.dpvn.shared.util.ListUtil;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InteractionService extends AbstractCrudService<Interaction> {

  private final SaleCustomerService saleCustomerService;

  public InteractionService(
      InteractionRepository repository, SaleCustomerService saleCustomerService) {
    super(repository);
    this.saleCustomerService = saleCustomerService;
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

  @Override
  @Transactional
  public Interaction create(Interaction entity) {
    // check if interactBy is in-charge (assinged mode) with customer, extends timelife
    Long saleId = entity.getInteractBy();
    Long customerId = entity.getCustomerId();
    if (saleId != null && customerId != null) {
      List<SaleCustomer> assignedCustomers =
          saleCustomerService.findSaleCustomersByOptions(
              entity.getInteractBy(),
              List.of(entity.getCustomerId()),
              RelationshipType.PIC,
              List.of(SaleCustomers.Reason.LEADER),
              null);
      if (ListUtil.isNotEmpty(assignedCustomers)) {
        assignedCustomers.sort(
            Comparator.comparing(
                SaleCustomer::getAvailableTo, Comparator.nullsFirst(Comparator.reverseOrder())));
        SaleCustomer latest = assignedCustomers.get(0);
        if (latest.getAvailableTo() != null && latest.getAvailableTo().isAfter(DateUtil.now())) {
          latest.setAvailableTo(
              DateUtil.now()
                  .plus(Globals.Customer.LIFE_TIME_LEADER_ASSIGNED_IN_DAYS, ChronoUnit.DAYS));
          saleCustomerService.update(latest);
        }
      }
    }

    return super.create(entity);
  }
}
