package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.CampaignDto;
import com.dpvn.shared.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "campaign")
public class Campaign extends BaseEntity<CampaignDto> {
  @Column(nullable = false)
  private String campaignName;

  @Column(columnDefinition = "TEXT")
  private String description;

  private Long campaignTypeId;

  @Column(nullable = false)
  private Instant startDate;

  @Column(nullable = false)
  private Instant endDate;

  private Integer steps;
  private Integer dispatchTypeId;

  @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CampaignStep> campaignSteps;

  public Campaign() {
    super(CampaignDto.class);
  }

  public String getCampaignName() {
    return campaignName;
  }

  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getCampaignTypeId() {
    return campaignTypeId;
  }

  public void setCampaignTypeId(Long campaignTypeId) {
    this.campaignTypeId = campaignTypeId;
  }

  public Instant getStartDate() {
    return startDate;
  }

  public void setStartDate(Instant startDate) {
    this.startDate = startDate;
  }

  public Instant getEndDate() {
    return endDate;
  }

  public void setEndDate(Instant endDate) {
    this.endDate = endDate;
  }

  public Integer getSteps() {
    return steps;
  }

  public void setSteps(Integer steps) {
    this.steps = steps;
  }

  public Set<CampaignStep> getCampaignSteps() {
    return campaignSteps;
  }

  public void setCampaignSteps(Set<CampaignStep> campaignSteps) {
    this.campaignSteps = campaignSteps;
  }

  public Integer getDispatchTypeId() {
    return dispatchTypeId;
  }
}
