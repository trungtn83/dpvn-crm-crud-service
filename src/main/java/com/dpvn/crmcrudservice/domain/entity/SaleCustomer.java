package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.sharedjpa.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "sale_customer")
public class SaleCustomer extends BaseEntity {
  private Long saleId; // DB id
  private Long customerId; // DB id

  // status: GOLD or TREASURE

  // INVOICE, ORDER, SELF-DIG, FRom SANDBANK....
  private String reasonCode; // assign by campaign, by self create, by leader....

  // value related to reason id
  @Column(columnDefinition = "TEXT")
  private String reasonRef;

  // tiếng việt giải thích chăng???
  @Column(columnDefinition = "TEXT")
  private String reasonNote;

  private Instant availableFrom;
  private Instant availableTo;

  @Column(columnDefinition = "TEXT")
  private String note;

  public String getReasonCode() {
    return reasonCode;
  }

  public void setReasonCode(String reasonCode) {
    this.reasonCode = reasonCode;
  }

  public String getReasonNote() {
    return reasonNote;
  }

  public void setReasonNote(String reasonNote) {
    this.reasonNote = reasonNote;
  }

  public Instant getAvailableFrom() {
    return availableFrom;
  }

  public void setAvailableFrom(Instant availableFrom) {
    this.availableFrom = availableFrom;
  }

  public Instant getAvailableTo() {
    return availableTo;
  }

  public void setAvailableTo(Instant availableTo) {
    this.availableTo = availableTo;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Long getSaleId() {
    return saleId;
  }

  public void setSaleId(Long saleId) {
    this.saleId = saleId;
  }

  public String getReasonRef() {
    return reasonRef;
  }

  public void setReasonRef(String reasonRef) {
    this.reasonRef = reasonRef;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }
}
