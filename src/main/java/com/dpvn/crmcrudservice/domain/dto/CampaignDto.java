package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.constant.DispatchType;
import com.dpvn.crmcrudservice.domain.entity.Campaign;
import com.dpvn.shared.domain.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignDto extends BaseDto<Campaign> {
  private String campaignName;

  private String description;
  private Long campaignTypeId;
  private Instant startDate;
  private Instant endDate;
  private Integer steps;
  private Integer dispatchTypeId = DispatchType.ROUND_ROBIN;

  private List<CampaignStepDto> campaignSteps;

  public CampaignDto() {
    super(Campaign.class);
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

  public List<CampaignStepDto> getCampaignSteps() {
    return campaignSteps;
  }

  public void setCampaignSteps(List<CampaignStepDto> campaignSteps) {
    this.campaignSteps = campaignSteps;
  }

  public Integer getDispatchTypeId() {
    return dispatchTypeId;
  }

  public void setDispatchTypeId(Integer dispatchTypeId) {
    this.dispatchTypeId = dispatchTypeId;
  }
}
