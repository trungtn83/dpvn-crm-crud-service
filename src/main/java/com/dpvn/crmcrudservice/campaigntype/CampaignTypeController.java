package com.dpvn.crmcrudservice.campaigntype;

import com.dpvn.crmcrudservice.AbstractController;
import com.dpvn.crmcrudservice.domain.dto.CampaignTypeDto;
import com.dpvn.crmcrudservice.domain.entity.CampaignType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crmcrud/campaign-type")
public class CampaignTypeController extends AbstractController<CampaignType, CampaignTypeDto> {
  public CampaignTypeController(CampaignTypeService campaignTypeService) {
    super(campaignTypeService);
  }
}
