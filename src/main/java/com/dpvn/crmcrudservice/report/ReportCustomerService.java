//package com.dpvn.crmcrudservice.report;
//
//import com.dpvn.crmcrudservice.domain.entity.report.CustomerBySeller;
//import com.dpvn.crmcrudservice.domain.entity.report.InteractionBySeller;
//import com.dpvn.crmcrudservice.domain.entity.report.TaskBySeller;
//import com.dpvn.crmcrudservice.repository.InteractionRepository;
//import com.dpvn.crmcrudservice.repository.SaleCustomerRepository;
//import com.dpvn.crmcrudservice.repository.TaskRepository;
//import com.dpvn.sharedcore.util.StringUtil;
//import java.time.Instant;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ReportCustomerService {
//  private final SaleCustomerRepository saleCustomerRepository;
//  private final InteractionRepository interactionRepository;
//  private final TaskRepository taskRepository;
//
//  public ReportCustomerService(
//      SaleCustomerRepository saleCustomerRepository,
//      InteractionRepository interactionRepository,
//      TaskRepository taskRepository) {
//    this.saleCustomerRepository = saleCustomerRepository;
//    this.interactionRepository = interactionRepository;
//    this.taskRepository = taskRepository;
//  }
//
//  public Map<Long, List<InteractionBySeller>> reportInteractionsBySellers(
//      List<Long> sellerIds, Instant fromDate, Instant toDate) {
//    List<Object[]> os =
//        interactionRepository.reportInteractionsBySellers(sellerIds, fromDate, toDate);
//    List<InteractionBySeller> interactionBySellers =
//        os.stream().map(this::tranformToInteractionBySeller).toList();
//    return interactionBySellers.stream()
//        .collect(Collectors.groupingBy(InteractionBySeller::getSellerId));
//  }
//
//  private InteractionBySeller tranformToInteractionBySeller(Object[] o) {
//    InteractionBySeller interaction = new InteractionBySeller();
//    interaction.setId(Long.parseLong(StringUtil.toString(o[0])));
//    interaction.setSellerId(Long.parseLong(StringUtil.toString(o[1])));
//    interaction.setCustomerId(Long.parseLong(StringUtil.toString(o[2])));
//    interaction.setCustomerName(StringUtil.toString(o[3]));
//    interaction.setMobilePhone(StringUtil.toString(o[4]));
//    interaction.setTitle(StringUtil.toString(o[5]));
//    interaction.setContent(StringUtil.toString(o[6]));
//    interaction.setVisibility(Integer.parseInt(StringUtil.toString(o[7])));
//    interaction.setCreatedDate((Instant) o[8]);
//    return interaction;
//  }
//
//  public Map<Long, List<TaskBySeller>> reportTasksBySellers(
//      List<Long> sellerIds, Instant fromDate, Instant toDate) {
//    List<Object[]> os = taskRepository.reportTasksBySellers(sellerIds, fromDate, toDate);
//    List<TaskBySeller> taskBySellers = os.stream().map(this::tranformToTaskBySeller).toList();
//    return taskBySellers.stream().collect(Collectors.groupingBy(TaskBySeller::getSellerId));
//  }
//
//  private TaskBySeller tranformToTaskBySeller(Object[] o) {
//    TaskBySeller task = new TaskBySeller();
//    task.setId(Long.parseLong(StringUtil.toString(o[0])));
//    task.setSellerId(Long.parseLong(StringUtil.toString(o[1])));
//    task.setName(StringUtil.toString(o[2]));
//    task.setTitle(StringUtil.toString(o[3]));
//    task.setContent(StringUtil.toString(o[4]));
//    task.setFromDate((Instant) o[5]);
//    task.setToDate((Instant) o[6]);
//    task.setModifiedDate((Instant) o[7]);
//    return task;
//  }
//}
