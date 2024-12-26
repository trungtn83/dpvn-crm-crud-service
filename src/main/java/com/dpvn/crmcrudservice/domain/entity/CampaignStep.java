package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.CampaignStepDto;
import com.dpvn.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "campaign_step")
public class CampaignStep extends BaseEntity<CampaignStepDto> {
  @ManyToOne
  @JoinColumn(name = "campaign_id", referencedColumnName = "id")
  private Campaign campaign;

  private Integer stepOrder;
  private String stepName;

  @Column(columnDefinition = "TEXT")
  private String description;

  private Integer deadline;

  private Integer completionPercentage;

  public CampaignStep() {
    super(CampaignStepDto.class);
  }

  public Campaign getCampaign() {
    return campaign;
  }

  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
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

  public Integer getDeadline() {
    return deadline;
  }

  public void setDeadline(Integer deadline) {
    this.deadline = deadline;
  }

  public Integer getCompletionPercentage() {
    return completionPercentage;
  }

  public void setCompletionPercentage(Integer completionPercentage) {
    this.completionPercentage = completionPercentage;
  }
}
