package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.constant.SaleCustomers;
import com.dpvn.sharedcore.domain.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleCustomerDto extends BaseDto {
  private Long saleId;
  private Long customerId;
  // status: GOLD or TREASURE
  private String reasonCode; // assign by campaign, by self create, by leader....
  private String reasonRef;
  private String reasonNote;
  private Instant availableFrom;
  private Instant availableTo;
  private String note;

  public static String generateStatusName(String status) {
    return SaleCustomers.Status.GOLD.equals(status) ? "Vàng" : "Trang sức";
  }

  public static String generateReasonCodeName(String reasonCode) {
    if (SaleCustomers.Reason.SANDBANK.equals(reasonCode)) {
      return "Bãi cát";
    } else if (SaleCustomers.Reason.GOLDMINE.equals(reasonCode)) {
      return "Kho vàng";
    } else if (SaleCustomers.Reason.LEADER.equals(reasonCode)) {
      return "Được phân cong";
    } else if (SaleCustomers.Reason.CAMPAIGN.equals(reasonCode)) {
      return "Chiến dịch";
    }
    return "???";
  }

  public Long getSaleId() {
    return saleId;
  }

  public void setSaleId(Long saleId) {
    this.saleId = saleId;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

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

  public String getReasonRef() {
    return reasonRef;
  }

  public void setReasonRef(String reasonRef) {
    this.reasonRef = reasonRef;
  }
}
