package com.dpvn.crmcrudservice.campaigntype;

import com.dpvn.crmcrudservice.domain.dto.CampaignTypeDto;
import com.dpvn.crmcrudservice.domain.entity.CampaignType;
import com.dpvn.shared.controller.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaign-type")
public class CampaignTypeController extends AbstractCrudController<CampaignType, CampaignTypeDto> {
  public CampaignTypeController(CampaignTypeService campaignTypeService) {
    super(campaignTypeService);
  }
}
