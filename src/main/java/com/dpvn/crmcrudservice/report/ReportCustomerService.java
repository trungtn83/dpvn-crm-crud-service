package com.dpvn.crmcrudservice.report;

import com.dpvn.crmcrudservice.domain.entity.report.CustomerBySeller;
import com.dpvn.crmcrudservice.domain.entity.report.InteractionBySeller;
import com.dpvn.crmcrudservice.domain.entity.report.TaskBySeller;
import com.dpvn.crmcrudservice.repository.InteractionRepository;
import com.dpvn.crmcrudservice.repository.SaleCustomerRepository;
import com.dpvn.crmcrudservice.repository.TaskRepository;
import com.dpvn.shared.util.DateUtil;
import com.dpvn.shared.util.LocalDateUtil;
import com.dpvn.shared.util.StringUtil;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReportCustomerService {
  private final SaleCustomerRepository saleCustomerRepository;
  private final InteractionRepository interactionRepository;
  private final TaskRepository taskRepository;

  public ReportCustomerService(
      SaleCustomerRepository saleCustomerRepository, InteractionRepository interactionRepository, TaskRepository taskRepository) {
    this.saleCustomerRepository = saleCustomerRepository;
    this.interactionRepository = interactionRepository;
    this.taskRepository = taskRepository;
  }

  public Map<Long, List<CustomerBySeller>> reportCustomersBySellers(
      List<Long> sellerIds, Instant fromDate, Instant toDate) {
    List<Object[]> os =
        saleCustomerRepository.reportCustomersBySellers(sellerIds, fromDate, toDate);
    List<CustomerBySeller> customerBySellers =
        os.stream().map(this::tranformToCustomerBySeller).toList();
    return customerBySellers.stream().collect(Collectors.groupingBy(CustomerBySeller::getSellerId));
  }

  private CustomerBySeller tranformToCustomerBySeller(Object[] o) {
    CustomerBySeller customer = new CustomerBySeller();
    customer.setId(Long.parseLong(o[0].toString()));
    customer.setSellerId(Long.parseLong(o[1].toString()));
    customer.setReason(Integer.parseInt(o[2].toString()));
    customer.setCreatedDate((Instant) o[3]);
    customer.setName(o[4].toString());
    customer.setCode(o[5].toString());
    customer.setMobilePhone(o[6].toString());
    return customer;
  }

  public Map<Long, List<InteractionBySeller>> reportInteractionsBySellers(
      List<Long> sellerIds, Instant fromDate, Instant toDate) {
    List<Object[]> os =
        interactionRepository.reportInteractionsBySellers(sellerIds, fromDate, toDate);
    List<InteractionBySeller> interactionBySellers =
        os.stream().map(this::tranformToInteractionBySeller).toList();
    return interactionBySellers.stream()
        .collect(Collectors.groupingBy(InteractionBySeller::getSellerId));
  }

  private InteractionBySeller tranformToInteractionBySeller(Object[] o) {
    InteractionBySeller interaction = new InteractionBySeller();
    interaction.setId(Long.parseLong(o[0].toString()));
    interaction.setSellerId(Long.parseLong(o[1].toString()));
    interaction.setCustomerId(Long.parseLong(o[2].toString()));
    interaction.setCustomerName(o[3].toString());
    if (o[4] != null) {
      interaction.setMobilePhone(o[4].toString());
    }
    if (o[5] != null) {
      interaction.setTitle(o[5].toString());
    }
    interaction.setContent(o[6].toString());
    interaction.setVisibility(Integer.parseInt(o[7].toString()));
    interaction.setCreatedDate((Instant) o[8]);
    return interaction;
  }

  public Map<Long, List<TaskBySeller>> reportTasksBySellers(
      List<Long> sellerIds, Instant fromDate, Instant toDate) {
    List<Object[]> os = taskRepository.reportTasksBySellers(sellerIds, fromDate, toDate);
    List<TaskBySeller> taskBySellers = os.stream().map(this::tranformToTaskBySeller).toList();
    return taskBySellers.stream().collect(Collectors.groupingBy(TaskBySeller::getId));
  }

  private TaskBySeller tranformToTaskBySeller(Object[] o) {
    TaskBySeller task = new TaskBySeller();
    task.setId(Long.parseLong(o[0].toString()));
    task.setSellerId(Long.parseLong(o[1].toString()));
    if (o[2] != null) {
      task.setName(o[2].toString());
    }
    if (o[3] != null) {
      task.setTitle(o[3].toString());
    }
    if (o[4] != null) {
      task.setContent(o[4].toString());
    }
    if (o[5] != null) {
      task.setFromDate((Instant) o[5]);
    }
    if (o[6] != null) {
      task.setToDate((Instant) o[6]);
    }
    if (o[7] != null) {
      task.setModifiedDate((Instant) o[7]);
    }
    return task;
  }
}
