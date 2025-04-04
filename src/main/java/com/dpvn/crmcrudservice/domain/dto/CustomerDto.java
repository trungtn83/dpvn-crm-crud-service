package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.shared.domain.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto extends BaseDto<Customer> {
  private String customerCode;
  private String customerName;
  private Instant birthday;
  private Integer gender;
  private String mobilePhone;
  private String email;
  private String taxCode;
  private String pinCode;
  private Integer levelPoint;
  // PHARMACY, PHARMACIST, DOCTOR, HOSPITAL, CLINIC, OTHER...
  private Long customerTypeId;
  private String customerType;
  // for future
  private Long customerCategoryId;
  private String customerCategory;
  private Integer sourceId;
  private String sourceNote;
  private String notes;
  private String relationships;
  private String specialEvents;

  private List<CustomerReferenceDto> references = new ArrayList<>();
  private List<CustomerAddressDto> addresses = new ArrayList<>();

  public CustomerDto() {
    super(Customer.class);
  }

  public String getCustomerType() {
    return customerType;
  }

  public void setCustomerType(String customerType) {
    this.customerType = customerType;
  }

  public String getCustomerCategory() {
    return customerCategory;
  }

  public void setCustomerCategory(String customerCategory) {
    this.customerCategory = customerCategory;
  }

  public Long getCustomerCategoryId() {
    return customerCategoryId;
  }

  public void setCustomerCategoryId(Long customerCategoryId) {
    this.customerCategoryId = customerCategoryId;
  }

  public Integer getSourceId() {
    return sourceId;
  }

  public void setSourceId(Integer sourceId) {
    this.sourceId = sourceId;
  }

  public String getSourceNote() {
    return sourceNote;
  }

  public void setSourceNote(String sourceNote) {
    this.sourceNote = sourceNote;
  }

  public List<CustomerReferenceDto> getReferences() {
    return references;
  }

  public void setReferences(List<CustomerReferenceDto> references) {
    this.references = references;
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

  public Long getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(Long customerTypeId) {
    this.customerTypeId = customerTypeId;
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

  public String getPinCode() {
    return pinCode;
  }

  public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
  }

  public Instant getBirthday() {
    return birthday;
  }

  public void setBirthday(Instant birthday) {
    this.birthday = birthday;
  }

  public List<CustomerAddressDto> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<CustomerAddressDto> addresses) {
    this.addresses = addresses;
  }
}
