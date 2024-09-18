// package com.dpvn.crmcrudservice.campaign;
//
// import com.dpvn.crmcrudservice.AbstractService;
// import com.dpvn.crmcrudservice.customer.CustomerService;
// import com.dpvn.crmcrudservice.domain.Status;
// import com.dpvn.crmcrudservice.domain.constant.RelationshipType;
// import com.dpvn.crmcrudservice.domain.dto.CampaignDto;
// import com.dpvn.crmcrudservice.domain.entity.Campaign;
// import com.dpvn.crmcrudservice.domain.entity.CampaignStep;
// import com.dpvn.crmcrudservice.domain.entity.Customer;
// import com.dpvn.crmcrudservice.domain.entity.User;
// import com.dpvn.crmcrudservice.domain.entity.relationship.CampaignCustomer;
// import com.dpvn.crmcrudservice.domain.entity.relationship.CampaignUser;
// import com.dpvn.crmcrudservice.domain.entity.relationship.CustomerUser;
// import com.dpvn.crmcrudservice.repository.CampaignCustomRepository;
// import com.dpvn.crmcrudservice.repository.CampaignRepository;
// import com.dpvn.crmcrudservice.user.UserService;
// import com.dpvn.shared.exception.BadRequestException;
// import com.dpvn.shared.util.DateUtil;
// import java.time.Instant;
// import java.util.Comparator;
// import java.util.List;
// import java.util.Set;
// import java.util.stream.Collectors;
// import org.springframework.stereotype.Service;
//
// @Service
// public class CampaignService extends AbstractService<Campaign> {
//  private final UserService userService;
//  private final CustomerService customerService;
//  private final CampaignCustomRepository customRepository;
//
//  public CampaignService(
//      CampaignRepository repository,
//      UserService userService,
//      CustomerService customerService,
//      CampaignCustomRepository customRepository) {
//    super(repository);
//    this.userService = userService;
//    this.customerService = customerService;
//    this.customRepository = customRepository;
//  }
//
//  @Override
//  public void sync(List<Campaign> entities) {
//    throw new UnsupportedOperationException("Not supported yet.");
//  }
//
//  public List<Campaign> searchCampaigns(
//      String campaignName,
//      Long picUser,
//      Long typeId,
//      Integer status,
//      Instant fromDate,
//      Instant toDate) {
//    return customRepository.searchCampaigns(
//        campaignName, picUser, typeId, status, fromDate, toDate);
//  }
//
//  public void createCampaign(CampaignDto campaignDto, List<Long> saleIds, List<Long> customerIds)
// {
//    List<User> sales = userService.findByIds(saleIds);
//    List<Customer> customers = customerService.findByIds(customerIds);
//    // validate all customers are free now
//    validateInPoolCustomers(customers);
//
//    final Campaign entity = campaignDto.toEntity();
//    entity.getCampaignSteps().forEach(step -> step.setCampaign(entity));
//
//    Set<CampaignUser> campaignUsers =
//        sales.stream().map(sale -> new CampaignUser(entity, sale)).collect(Collectors.toSet());
//    entity.setCampaignUsers(campaignUsers);
//
//    Set<CampaignCustomer> campaignCustomers =
//        customers.stream()
//            .map(customer -> new CampaignCustomer(entity, customer))
//            .collect(Collectors.toSet());
//    entity.setCampaignCustomers(campaignCustomers);
//
//    Campaign campaign = repository.save(entity);
//    dispatchCampaign(campaign);
//  }
//
//  private void validateInPoolCustomers(List<Customer> customers) {
//    StringBuilder errors = new StringBuilder();
//    for (Customer customer : customers) {
//      CustomerUser pic =
//          customer.getCustomerUsers().stream()
//              .filter(
//                  cu ->
//                      cu.getRelationshipTypeId() == RelationshipType.PIC
//                          && cu.getStatus() == Status.ACTIVE)
//              .findFirst()
//              .orElse(null);
//      if (pic != null) {
//        String error =
//            String.format(
//                "Customer [%d,%s] is handled by [%d, %s]\n",
//                customer.getId(),
//                customer.getCustomerName(),
//                pic.getUser().getId(),
//                pic.getUser().getFullName());
//        errors.append(error);
//      } else {
//        CustomerUser involved =
//            customer.getCustomerUsers().stream()
//                .filter(
//                    cu ->
//                        cu.getRelationshipTypeId() == RelationshipType.INVOLVED
//                            && cu.getStatus() == Status.ACTIVE)
//                .findFirst()
//                .orElse(null);
//        if (involved != null) {
//          String error =
//              String.format(
//                  "Customer [%d,%s] is took care by [%d, %s]\n",
//                  customer.getId(),
//                  customer.getCustomerName(),
//                  involved.getUser().getId(),
//                  involved.getUser().getFullName());
//          errors.append(error);
//        }
//      }
//    }
//    if (!errors.isEmpty()) {
//      throw new BadRequestException(errors.toString());
//    }
//  }
//
//  public void dispatchCampaign(Campaign campaign) {
//    // hardcode for RR
//    List<User> sales = campaign.getCampaignUsers().stream().map(CampaignUser::getUser).toList();
//    int saleTotal = sales.size();
//
//    List<Customer> customers =
//        campaign.getCampaignCustomers().stream().map(CampaignCustomer::getCustomer).toList();
//    for (int i = 0; i < customers.size(); i++) {
//      User sale = sales.get(i % saleTotal);
//      Customer customer = customers.get(i);
//      if (sale.getCustomerUsers().stream()
//          .noneMatch(cu -> cu.getCustomer().getId().equals(customer.getId()))) {
//        sale.getCustomerUsers()
//            .add(new CustomerUser(customer, sale, RelationshipType.INVOLVED, DateUtil.now()));
//      }
//    }
//
//    userService.saveAll(sales);
//  }
//
//  public void updateCustomerProgress(Long campaignId, Long customerId, Integer progressId) {
//    Campaign campaign =
//        repository
//            .findById(campaignId)
//            .orElseThrow(() -> new BadRequestException("Campaign not found"));
//    CampaignCustomer campaignCustomer =
//        campaign.getCampaignCustomers().stream()
//            .filter(cc -> cc.getCustomer().getId().equals(customerId))
//            .findFirst()
//            .orElseThrow();
//
//    // validate if customer in last step, cannot update progress
//    CampaignStep lastStep =
//        campaign.getCampaignSteps().stream()
//            .max(Comparator.comparing(CampaignStep::getStepOrder))
//            .orElseThrow();
//    if (lastStep.getStepOrder() == campaignCustomer.getProgressId()) {
//      throw new BadRequestException("Customer is in last step, cannot update progress");
//    }
//
//    campaignCustomer.setProgressId(progressId);
//    repository.save(campaign);
//  }
// }
