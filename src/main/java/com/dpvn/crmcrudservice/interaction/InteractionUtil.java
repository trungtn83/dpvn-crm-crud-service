package com.dpvn.crmcrudservice.interaction;

import com.dpvn.crmcrudservice.domain.constant.Interactions;
import com.dpvn.crmcrudservice.domain.entity.Interaction;

public class InteractionUtil {
  private InteractionUtil() {}

  public static Interaction generateSystemInteraction(
      Long userId, Long customerId, Long campaignId, String title, String content) {
    Interaction interaction = new Interaction();
    interaction.setTypeId(-1);
    interaction.setType("Hệ thống");
    interaction.setCreatedBy(-1L);
    interaction.setInteractBy(userId);
    interaction.setCampaignId(campaignId);
    interaction.setCustomerId(customerId);
    interaction.setTitle(title);
    interaction.setContent(content);
    interaction.setVisibility(Interactions.Visibility.PUBLIC);
    return interaction;
  }
}
