package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.entity.CustomerLevelDetail;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerLevelDetailDto extends BaseDto {
  private Long customerLevelId;
  private String levelName;
  private String description;
  private Integer basePoint;

  @Override
  public CustomerLevelDetail toEntity() {
    return BeanMapper.instance().map(this, CustomerLevelDetail.class);
  }

  public Long getCustomerLevelId() {
    return customerLevelId;
  }

  public void setCustomerLevelId(Long customerLevelId) {
    this.customerLevelId = customerLevelId;
  }

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getBasePoint() {
    return basePoint;
  }

  public void setBasePoint(Integer basePoint) {
    this.basePoint = basePoint;
  }
}
