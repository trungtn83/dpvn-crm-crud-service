package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.entity.CampaignType;
import com.dpvn.crmcrudservice.domain.entity.CampaignTypeStep;
import com.dpvn.shared.domain.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignTypeDto extends BaseDto<CampaignType> {
  private String campaignTypeName;

  private String description;
  private Integer steps;

  public CampaignTypeDto() {
    super(CampaignType.class);
  }

  private Set<CampaignTypeStep> campaignTypeSteps;

  public String getCampaignTypeName() {
    return campaignTypeName;
  }

  public void setCampaignTypeName(String campaignTypeName) {
    this.campaignTypeName = campaignTypeName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getSteps() {
    return steps;
  }

  public void setSteps(Integer steps) {
    this.steps = steps;
  }

  public Set<CampaignTypeStep> getCampaignTypeSteps() {
    return campaignTypeSteps;
  }

  public void setCampaignTypeSteps(Set<CampaignTypeStep> campaignTypeSteps) {
    this.campaignTypeSteps = campaignTypeSteps;
  }
}
