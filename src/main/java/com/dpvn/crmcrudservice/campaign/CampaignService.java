package com.dpvn.crmcrudservice.campaign;

import com.dpvn.crmcrudservice.customer.CustomerService;
import com.dpvn.crmcrudservice.customer.SaleCustomerService;
import com.dpvn.crmcrudservice.customer.SaleCustomerUtil;
import com.dpvn.crmcrudservice.domain.constant.Customers;
import com.dpvn.crmcrudservice.domain.constant.RelationshipType;
import com.dpvn.crmcrudservice.domain.constant.SaleCustomers;
import com.dpvn.crmcrudservice.domain.entity.Campaign;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.crmcrudservice.repository.CampaignCustomRepository;
import com.dpvn.crmcrudservice.repository.CampaignRepository;
import com.dpvn.crmcrudservice.repository.CustomerRepository;
import com.dpvn.crmcrudservice.user.UserService;
import com.dpvn.shared.service.AbstractCrudService;
import com.dpvn.shared.util.FastMap;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CampaignService extends AbstractCrudService<Campaign> {
  private final UserService userService;
  private final CustomerService customerService;
  private final CampaignCustomRepository customRepository;
  private final SaleCustomerService saleCustomerService;
  private final CustomerRepository customerRepository;

  public CampaignService(
      CampaignRepository repository,
      UserService userService,
      CustomerService customerService,
      CampaignCustomRepository customRepository,
      SaleCustomerService saleCustomerService,
      CustomerRepository customerRepository) {
    super(repository);
    this.userService = userService;
    this.customerService = customerService;
    this.customRepository = customRepository;
    this.saleCustomerService = saleCustomerService;
    this.customerRepository = customerRepository;
  }

  @Override
  public void sync(List<Campaign> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public List<Campaign> searchCampaigns(
      String campaignName,
      Long picUser,
      Long typeId,
      Integer status,
      Instant fromDate,
      Instant toDate) {
    return customRepository.searchCampaigns(
        campaignName, picUser, typeId, status, fromDate, toDate);
  }

  public void createCampaignUsers(Campaign campaign, List<Long> saleIds, List<Long> customerIds) {
    List<User> sales = userService.findByIds(saleIds);
    List<Customer> customers = customerService.findByIds(customerIds);
    // TODO: validate all customers are free now
    validateInPoolCustomers(customers);

    campaign.getCampaignSteps().forEach(step -> step.setCampaign(campaign));

    campaign.setUsers(new HashSet<>(sales));
    campaign.setCustomers(new HashSet<>(customers));

    Campaign dbCampaign = save(campaign);
    dispatchCampaign(dbCampaign);
  }

  public void assignCustomersToCampaign(
      Long campaignId, List<Long> saleIds, List<Long> customerIds) {
    Campaign campaign = findById(campaignId).orElseThrow();
    List<User> sales = userService.findByIds(saleIds);
    List<Customer> customers = customerService.findByIds(customerIds);

    // add more sale and customer into campaign
    sales.forEach(
        sale -> {
          if (campaign.getUsers().stream().noneMatch(u -> u.getId().equals(sale.getId()))) {
            campaign.getUsers().add(sale);
          }
        });
    customers.forEach(
        customer -> {
          if (campaign.getCustomers().stream().noneMatch(c -> c.getId().equals(customer.getId()))) {
            campaign.getCustomers().add(customer);
          }
        });
    save(campaign);

    // update sale status to VERIFY
    customerIds.forEach(
        customerId -> {
          customerService.update(
              customerId,
              FastMap.create().add("active", true).add("status", Customers.Status.VERIFYING));
        });

    // dispatch only customer into only sales, does not affect to existed ones in current campaign
    dispatchCampaignWithSpecificCustomersToSales(campaign, customers, sales);
  }

  private void validateInPoolCustomers(List<Customer> customers) {
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
  }

  // re-arrange all customers to sales in campaign
  public void dispatchCampaign(Campaign campaign) {
    List<User> sales = campaign.getUsers().stream().toList();
    List<Customer> customers = campaign.getCustomers().stream().toList();
    dispatchCampaignWithSpecificCustomersToSales(campaign, customers, sales);
  }

  private void dispatchCampaignWithSpecificCustomersToSales(
      Campaign campaign, List<Customer> customers, List<User> sales) {
    // hardcode for Round-Robin
    int saleTotal = sales.size();
    for (int i = 0; i < customers.size(); i++) {
      User sale = sales.get(i % saleTotal);
      Customer customer = customers.get(i);
      if (isNotDispatchCustomer(campaign, customer)) {
        SaleCustomer saleCustomer = SaleCustomerUtil.generateSaleCustomer(customer, sale, campaign);
        customerService.assign(sale, customer, saleCustomer);
      }
    }
  }

  private boolean isNotDispatchCustomer(Campaign campaign, Customer customer) {
    List<SaleCustomer> saleCustomers =
        saleCustomerService.findSaleCustomersByOptions(
            null,
            List.of(customer.getId()),
            RelationshipType.PIC,
            List.of(SaleCustomers.Reason.CAMPAIGN),
            campaign.getId().toString());
    return saleCustomers.isEmpty();
  }

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
}
