package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.CampaignTypeStepDto;
import com.dpvn.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "campaign_type_step")
public class CampaignTypeStep extends BaseEntity<CampaignTypeStepDto> {
  @ManyToOne
  @JoinColumn(name = "campaign_type_id", referencedColumnName = "id")
  private CampaignType campaignType;

  private Integer stepOrder;
  private String stepName;

  @Column(columnDefinition = "TEXT")
  private String description;

  private Integer deadline;

  private Integer completionPercentage;

  public CampaignTypeStep() {
    super(CampaignTypeStepDto.class);
  }

  public CampaignType getCampaignType() {
    return campaignType;
  }

  public void setCampaignType(CampaignType campaignType) {
    this.campaignType = campaignType;
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

  public Integer getDeadline() {
    return deadline;
  }

  public void setDeadline(Integer deadline) {
    this.deadline = deadline;
  }
}
