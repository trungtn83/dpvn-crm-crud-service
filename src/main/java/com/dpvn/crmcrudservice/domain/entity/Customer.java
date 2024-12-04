package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.BaseEntity;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.constant.Customers;
import com.dpvn.crmcrudservice.domain.constant.Status;
import com.dpvn.crmcrudservice.domain.dto.CustomerDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String customerId;
  private String customerCode;

  @Column(columnDefinition = "TEXT")
  private String customerName;

  private Instant birthday;
  private Integer gender;
  private String mobilePhone;
  private String email;

  @Column(columnDefinition = "TEXT")
  private String address;

  private Long addressCode; // store ward code or address id?
  private String taxCode;
  private String pinCode;
  private Integer levelPoint = 0;
  private Integer status = Status.ACTIVE;
  private Integer customerTypeId = Customers.Type.PHARMACIST; // PHARMACY, DOCRTOR, COMPANY...
  private Integer customerCategoryId = Customers.Category.WHOLE_SALE; // wholesle, retail, ....
  private Integer sourceId = Customers.Source.KIOTVIET;

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

  @Override
  public CustomerDto toDto() {
    return BeanMapper.instance().map(this, CustomerDto.class);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public Instant getBirthday() {
    return birthday;
  }

  public void setBirthday(Instant birthday) {
    this.birthday = birthday;
  }
}
