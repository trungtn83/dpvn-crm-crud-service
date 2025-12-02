package com.dpvn.crmcrudservice.domain.dto;

import java.time.Instant;

public class TreasureCustomerDto extends CustomerDto {
  private Long soldById;
  private Instant availableTo;

  public Instant getAvailableTo() {
    return availableTo;
  }

  public void setAvailableTo(Instant availableTo) {
    this.availableTo = availableTo;
  }

  public Long getSoldById() {
    return soldById;
  }

  public void setSoldById(Long soldById) {
    this.soldById = soldById;
  }
}
