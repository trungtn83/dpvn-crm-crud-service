package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.entity.CampaignTypeStep;
import com.dpvn.shared.domain.BaseDto;
import com.dpvn.shared.domain.BeanMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignTypeStepDto extends BaseDto {
  private Long campaignTypeId;
  private Integer stepOrder;
  private String stepName;

  private String description;

  private Integer completionPercentage;

  @Override
  public CampaignTypeStep toEntity() {
    return BeanMapper.instance().map(this, CampaignTypeStep.class);
  }

  public Long getCampaignTypeId() {
    return campaignTypeId;
  }

  public void setCampaignTypeId(Long campaignTypeId) {
    this.campaignTypeId = campaignTypeId;
  }

  public Integer getStepOrder() {
    return stepOrder;
  }

  public void setStepOrder(Integer stepOrder) {
    this.stepOrder = stepOrder;
  }

  public String getStepName() {
    return stepName;
  }

  public void setStepName(String stepName) {
    this.stepName = stepName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getCompletionPercentage() {
    return completionPercentage;
  }

  public void setCompletionPercentage(Integer completionPercentage) {
    this.completionPercentage = completionPercentage;
  }
}
