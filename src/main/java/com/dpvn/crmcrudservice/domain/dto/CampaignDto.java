package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.Status;
import com.dpvn.crmcrudservice.domain.constant.DispatchType;
import com.dpvn.crmcrudservice.domain.entity.Campaign;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignDto extends BaseDto {
  private String campaignName;

  private String description;
  private Long campaignTypeId;
  private Instant startDate;
  private Instant endDate;
  private Integer steps;
  private Integer status = Status.ACTIVE;
  private Integer dispatchTypeId = DispatchType.ROUND_ROBIN;

  private List<CampaignStepDto> campaignSteps;

  @Override
  public Campaign toEntity() {
    return BeanMapper.instance().map(this, Campaign.class);
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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
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
