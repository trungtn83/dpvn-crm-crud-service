package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomerCategory;
import com.dpvn.shared.domain.BaseDto;
import com.dpvn.shared.domain.BeanMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleCustomerCategoryDto extends BaseDto {
  private Long saleId;
  private String code;
  private String name;
  private String description;

  @Override
  public SaleCustomerCategory toEntity() {
    return BeanMapper.instance().map(this, SaleCustomerCategory.class);
  }

  public Long getSaleId() {
    return saleId;
  }

  public void setSaleId(Long saleId) {
    this.saleId = saleId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
