package com.dpvn.crmcrudservice.interaction;

import com.dpvn.crmcrudservice.domain.constant.RelationshipType;
import com.dpvn.crmcrudservice.domain.constant.SaleCustomers;
import com.dpvn.crmcrudservice.domain.constant.Visibility;
import com.dpvn.crmcrudservice.domain.entity.Campaign;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.shared.util.DateUtil;

import java.time.temporal.ChronoUnit;

public class InteractionUtil {
  private InteractionUtil() {}

  public static Interaction generateSystemInteraction(Long userId, Long customerId, Long campaignId, String title, String content) {
    Interaction interaction = new Interaction();
    interaction.setTypeId(-1);
    interaction.setType("Hệ thống");
    interaction.setCreatedBy(-1L);
    interaction.setInteractBy(userId);
    interaction.setCampaignId(campaignId);
    interaction.setCustomerId(customerId);
    interaction.setTitle(title);
    interaction.setContent(content);
    interaction.setVisibility(Visibility.PUBLIC);
    return interaction;
  }
}
