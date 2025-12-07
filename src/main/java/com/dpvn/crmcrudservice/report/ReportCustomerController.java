// package com.dpvn.crmcrudservice.report;
//
// import com.dpvn.crmcrudservice.domain.entity.report.CustomerBySeller;
// import com.dpvn.crmcrudservice.domain.entity.report.InteractionBySeller;
// import com.dpvn.crmcrudservice.domain.entity.report.TaskBySeller;
// import com.dpvn.sharedcore.util.DateUtil;
// import com.dpvn.sharedcore.util.FastMap;
// import com.dpvn.sharedcore.util.LocalDateUtil;
// import java.time.Instant;
// import java.util.List;
// import java.util.Map;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// @RestController
// @RequestMapping("/report")
// public class ReportCustomerController {
//  private final ReportCustomerService reportCustomerService;
//
//  public ReportCustomerController(ReportCustomerService reportCustomerService) {
//    this.reportCustomerService = reportCustomerService;
//  }
//
//  /**
//   * - sellerIds: List<Long>
//   * - fromDate: string -> yyyy-MM-dd
//   * - toDate: string -> yyyy-MM-dd
//   */
//  @PostMapping("/customer/by-sellers")
//  public Map<Long, List<CustomerBySeller>> reportCustomersBySellers(@RequestBody FastMap body) {
//    List<Long> sellerIds = body.getListClass("sellerIds", Long.class);
//    String fromDateStr = body.getString("fromDate");
//    String toDateStr = body.getString("toDate");
//    Instant fromDate = DateUtil.from(LocalDateUtil.from(fromDateStr));
//    Instant toDate = DateUtil.from(LocalDateUtil.from(toDateStr));
//    return reportCustomerService.reportCustomersBySellers(sellerIds, fromDate, toDate);
//  }
//
//  /**
//   * - sellerIds: List<Long>
//   * - fromDate: string -> yyyy-MM-dd
//   * - toDate: string -> yyyy-MM-dd
//   */
//  @PostMapping("/interaction/by-sellers")
//  public Map<Long, List<InteractionBySeller>> reportInteractionsBySellers(
//      @RequestBody FastMap body) {
//    List<Long> sellerIds = body.getListClass("sellerIds", Long.class);
//    String fromDateStr = body.getString("fromDate");
//    String toDateStr = body.getString("toDate");
//    Instant fromDate = DateUtil.from(LocalDateUtil.from(fromDateStr));
//    Instant toDate = DateUtil.from(LocalDateUtil.from(toDateStr));
//    return reportCustomerService.reportInteractionsBySellers(sellerIds, fromDate, toDate);
//  }
//
//  /**
//   * - sellerIds: List<Long>
//   * - fromDate: string -> yyyy-MM-dd
//   * - toDate: string -> yyyy-MM-dd
//   */
//  @PostMapping("/task/by-sellers")
//  public Map<Long, List<TaskBySeller>> reportTasksBySellers(@RequestBody FastMap body) {
//    List<Long> sellerIds = body.getListClass("sellerIds", Long.class);
//    String fromDateStr = body.getString("fromDate");
//    String toDateStr = body.getString("toDate");
//    Instant fromDate = DateUtil.from(LocalDateUtil.from(fromDateStr));
//    Instant toDate = DateUtil.from(LocalDateUtil.from(toDateStr));
//    return reportCustomerService.reportTasksBySellers(sellerIds, fromDate, toDate);
//  }
// }
