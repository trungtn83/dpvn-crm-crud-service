package com.dpvn.crmcrudservice.domain.dto;

import java.time.Instant;
import java.util.List;

public class GoldCustomerDto extends CustomerDto {
  private List<Long> saleIds;
  private Instant availableTo;
  private String reasonCode;
  private Long saleCustomerCategoryId;

  public Instant getAvailableTo() {
    return availableTo;
  }

  public void setAvailableTo(Instant availableTo) {
    this.availableTo = availableTo;
  }

  public Long getSaleCustomerCategoryId() {
    return saleCustomerCategoryId;
  }

  public void setSaleCustomerCategoryId(Long saleCustomerCategoryId) {
    this.saleCustomerCategoryId = saleCustomerCategoryId;
  }

  public String getReasonCode() {
    return reasonCode;
  }

  public void setReasonCode(String reasonCode) {
    this.reasonCode = reasonCode;
  }

  public List<Long> getSaleIds() {
    return saleIds;
  }

  public void setSaleIds(List<Long> saleIds) {
    this.saleIds = saleIds;
  }
}
