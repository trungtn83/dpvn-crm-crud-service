package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.entity.CampaignType;
import com.dpvn.crmcrudservice.domain.entity.CampaignTypeStep;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignTypeDto extends BaseDto {
  private String campaignTypeName;

  private String description;

  private Integer status;
  private Integer steps;

  @Override
  public CampaignType toEntity() {
    return BeanMapper.instance().map(this, CampaignType.class);
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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
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
