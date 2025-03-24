package com.dpvn.crmcrudservice.domain.entity.report;

import java.time.Instant;

public class CustomerBySeller {
  private Long id;
  private String name;
  private String code;
  private String mobilePhone;
  private int reason;
  private Instant createdDate;
  private Long sellerId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public int getReason() {
    return reason;
  }

  public void setReason(int reason) {
    this.reason = reason;
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  public Long getSellerId() {
    return sellerId;
  }

  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }
}
