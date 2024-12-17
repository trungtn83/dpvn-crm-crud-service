package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.shared.domain.BaseDto;
import com.dpvn.shared.domain.BeanMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleCustomerDto extends BaseDto {
  private Long saleId;
  private Long customerId;
  private CustomerDto customerDto;
  private Integer relationshipType; // PIC or EVOLVE
  private Integer reasonId; // assign by campaign, by self create, by leader....
  private String reasonRef;
  private String reasonNote;
  private Instant availableFrom;
  private Instant availableTo;
  private String note;

  @Override
  public SaleCustomer toEntity() {
    SaleCustomer entity = BeanMapper.instance().map(this, SaleCustomer.class);
    Customer customer = new Customer();
    customer.setId(customerId);
    entity.setCustomer(customer);
    return entity;
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

  public Integer getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(Integer relationshipType) {
    this.relationshipType = relationshipType;
  }

  public Integer getReasonId() {
    return reasonId;
  }

  public void setReasonId(Integer reasonId) {
    this.reasonId = reasonId;
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

  public CustomerDto getCustomerDto() {
    return customerDto;
  }

  public void setCustomerDto(CustomerDto customerDto) {
    this.customerDto = customerDto;
  }

  public String getReasonRef() {
    return reasonRef;
  }

  public void setReasonRef(String reasonRef) {
    this.reasonRef = reasonRef;
  }
}
