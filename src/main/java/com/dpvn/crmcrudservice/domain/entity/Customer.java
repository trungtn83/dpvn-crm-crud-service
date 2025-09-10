package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.CustomerDto;
import com.dpvn.shared.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity<CustomerDto> {
  private String customerCode;

  @Column(columnDefinition = "TEXT")
  private String customerName;

  private Instant birthday;
  private Integer gender;

  @Column(unique = true)
  private String mobilePhone;

  private String email;
  private String taxCode;
  private String pinCode;
  private Integer levelPoint;
  // PHARMACY, PHARMACIST, DOCTOR, HOSPITAL, CLINIC, OTHER...
  private Long customerTypeId;
  @Transient private String customerType;
  // still available for future
  private Integer customerCategoryId;
  private Integer sourceId;

  @Column(columnDefinition = "TEXT")
  private String sourceNote;

  @Column(columnDefinition = "TEXT")
  private String notes;

  @Column(columnDefinition = "TEXT")
  private String relationships;

  @Column(columnDefinition = "TEXT")
  private String specialEvents;

  @OneToMany(
      mappedBy = "customer",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<CustomerReference> references = new ArrayList<>();

  @OneToMany(
      mappedBy = "customer",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<CustomerAddress> addresses = new ArrayList<>();

  public Customer() {
    super(CustomerDto.class);
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
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

  public String getPinCode() {
    return pinCode;
  }

  public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
  }

  public String getSourceNote() {
    return sourceNote;
  }

  public void setSourceNote(String sourceNote) {
    this.sourceNote = sourceNote;
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

  public List<CustomerReference> getReferences() {
    return references;
  }

  public void setReferences(List<CustomerReference> references) {
    this.references = references;
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

  public Integer getCustomerCategoryId() {
    return customerCategoryId;
  }

  public void setCustomerCategoryId(Integer customerCategoryId) {
    this.customerCategoryId = customerCategoryId;
  }

  public Integer getSourceId() {
    return sourceId;
  }

  public void setSourceId(Integer sourceId) {
    this.sourceId = sourceId;
  }

  public Instant getBirthday() {
    return birthday;
  }

  public void setBirthday(Instant birthday) {
    this.birthday = birthday;
  }

  public List<CustomerAddress> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<CustomerAddress> addresses) {
    this.addresses = addresses;
  }

  public String getCustomerType() {
    return customerType;
  }

  public void setCustomerType(String customerType) {
    this.customerType = customerType;
  }
}
