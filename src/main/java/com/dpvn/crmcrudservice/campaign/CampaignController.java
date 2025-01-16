package com.dpvn.crmcrudservice.campaign;

import com.dpvn.crmcrudservice.domain.dto.CampaignDto;
import com.dpvn.crmcrudservice.domain.entity.Campaign;
import com.dpvn.shared.controller.AbstractCrudController;
import com.dpvn.shared.util.FastMap;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaign")
public class CampaignController extends AbstractCrudController<Campaign, CampaignDto> {
  public CampaignController(CampaignService campaignService) {
    super(campaignService);
  }

  @PostMapping("/search")
  public List<CampaignDto> searchCampaigns(@RequestBody FastMap body) {
    // TODO: validate permission what data should return to user
    List<Campaign> campaigns =
        ((CampaignService) service)
            .searchCampaigns(
                body.getString("campaignName"),
                body.getLong("picUser"),
                body.getLong("typeId"),
                body.getInt("status"),
                body.getInstant("fromDate"),
                body.getInstant("toDate"));
    return campaigns.stream().map(Campaign::toDto).toList();
  }

  @PostMapping("/create-with-sales-and-customers")
  public void createCampaignUsers(@RequestBody FastMap body) {
    // TODO: validate permission
    CampaignDto campaign = body.getClass("campaign", CampaignDto.class);
    List<Long> sales = body.getList("sales");
    List<Long> customers = body.getList("customers");
    ((CampaignService) service).createCampaignUsers(campaign.toEntity(), sales, customers);
  }

  //
  //  @PatchMapping("/{campaignId}/customer/{customerId}/{progressId}")
  //  public void updateCustomerProgress(
  //      @PathVariable Long campaignId,
  //      @PathVariable Long customerId,
  //      @PathVariable Integer progressId) {
  //    // TODO: validate permission if log in user is assigned to customer or not
  //    ((CampaignService) service).updateCustomerProgress(campaignId, customerId, progressId);
  //  }

  @PostMapping("/{id}/assign-customers-to-sales")
  public void assignToSaleInCampaign(@PathVariable Long id, @RequestBody FastMap body) {
    List<Long> saleIds = body.getListClass("saleIds", Long.class);
    List<Long> customerIds = body.getListClass("customerIds", Long.class);
    ((CampaignService) service).assignCustomersToCampaign(id, saleIds, customerIds);
  }
}
