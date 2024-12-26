package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.CampaignTypeDto;
import com.dpvn.shared.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "campaign_type")
public class CampaignType extends BaseEntity<CampaignTypeDto> {
  private String campaignTypeName;

  @Column(columnDefinition = "TEXT")
  private String description;

  private Integer steps;

  @OneToMany(mappedBy = "campaignType", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CampaignTypeStep> campaignTypeSteps;

  public CampaignType() {
    super(CampaignTypeDto.class);
  }

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

  public List<CampaignTypeStep> getCampaignTypeSteps() {
    return campaignTypeSteps;
  }

  public void setCampaignTypeSteps(List<CampaignTypeStep> campaignTypeSteps) {
    this.campaignTypeSteps = campaignTypeSteps;
  }
}
