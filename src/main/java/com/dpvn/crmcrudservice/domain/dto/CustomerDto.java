package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto extends BaseDto {
  private String customerName;
  private String customerCode;
  private Integer gender;
  private String mobilePhone;
  private String email;
  private String address;
  private Long addressCode; // store ward code or address id?
  private String taxCode;
  private Integer levelPoint;
  private Integer status;
  private Integer customerTypeId;

  private Long sourceId;
  private String source;
  private String sourceNote;
  private Long internalSourceId;
  private String internalSource;
  private String zaloId;
  private String zaloName;
  private String facebookId;
  private String facebookName;
  private String tiktokId;
  private String tiktokName;
  private String shopeeId;
  private String shopeeName;
  private String otherId;
  private String otherName;

  private String notes;
  private String relationships;
  private String specialEvents;

  @Override
  public Customer toEntity() {
    return BeanMapper.instance().map(this, Customer.class);
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Long getAddressCode() {
    return addressCode;
  }

  public void setAddressCode(Long addressCode) {
    this.addressCode = addressCode;
  }

  public String getTaxCode() {
    return taxCode;
  }

  public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
  }

  public Integer getLevelPoint() {
    return levelPoint;
  }

  public void setLevelPoint(Integer levelPoint) {
    this.levelPoint = levelPoint;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(Integer customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public Long getSourceId() {
    return sourceId;
  }

  public void setSourceId(Long sourceId) {
    this.sourceId = sourceId;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getSourceNote() {
    return sourceNote;
  }

  public void setSourceNote(String sourceNote) {
    this.sourceNote = sourceNote;
  }

  public Long getInternalSourceId() {
    return internalSourceId;
  }

  public void setInternalSourceId(Long internalSourceId) {
    this.internalSourceId = internalSourceId;
  }

  public String getInternalSource() {
    return internalSource;
  }

  public void setInternalSource(String internalSource) {
    this.internalSource = internalSource;
  }

  public String getZaloId() {
    return zaloId;
  }

  public void setZaloId(String zaloId) {
    this.zaloId = zaloId;
  }

  public String getZaloName() {
    return zaloName;
  }

  public void setZaloName(String zaloName) {
    this.zaloName = zaloName;
  }

  public String getFacebookId() {
    return facebookId;
  }

  public void setFacebookId(String facebookId) {
    this.facebookId = facebookId;
  }

  public String getFacebookName() {
    return facebookName;
  }

  public void setFacebookName(String facebookName) {
    this.facebookName = facebookName;
  }

  public String getTiktokId() {
    return tiktokId;
  }

  public void setTiktokId(String tiktokId) {
    this.tiktokId = tiktokId;
  }

  public String getTiktokName() {
    return tiktokName;
  }

  public void setTiktokName(String tiktokName) {
    this.tiktokName = tiktokName;
  }

  public String getShopeeId() {
    return shopeeId;
  }

  public void setShopeeId(String shopeeId) {
    this.shopeeId = shopeeId;
  }

  public String getShopeeName() {
    return shopeeName;
  }

  public void setShopeeName(String shopeeName) {
    this.shopeeName = shopeeName;
  }

  public String getOtherId() {
    return otherId;
  }

  public void setOtherId(String otherId) {
    this.otherId = otherId;
  }

  public String getOtherName() {
    return otherName;
  }

  public void setOtherName(String otherName) {
    this.otherName = otherName;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getRelationships() {
    return relationships;
  }

  public void setRelationships(String relationships) {
    this.relationships = relationships;
  }

  public String getSpecialEvents() {
    return specialEvents;
  }

  public void setSpecialEvents(String specialEvents) {
    this.specialEvents = specialEvents;
  }
}
