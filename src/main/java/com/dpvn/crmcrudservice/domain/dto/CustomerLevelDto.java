package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.entity.CustomerLevel;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerLevelDto extends BaseDto {
  private String customerLevelName;
  private String description;
  private Instant startDate;
  private Instant endDate;
  private Integer status;

  @Override
  public CustomerLevel toEntity() {
    return BeanMapper.instance().map(this, CustomerLevel.class);
  }

  public String getCustomerLevelName() {
    return customerLevelName;
  }

  public void setCustomerLevelName(String customerLevelName) {
    this.customerLevelName = customerLevelName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
