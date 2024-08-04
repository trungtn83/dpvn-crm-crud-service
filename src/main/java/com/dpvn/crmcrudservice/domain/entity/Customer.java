package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.BaseEntity;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.Status;
import com.dpvn.crmcrudservice.domain.dto.CustomerDto;
import com.dpvn.crmcrudservice.domain.entity.relationship.CampaignCustomer;
import com.dpvn.crmcrudservice.domain.entity.relationship.CustomerUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String customerName;

  @Column(unique = true, nullable = false)
  private String customerCode;

  private Integer gender;

  private String mobilePhone;

  private String email;

  @Column(columnDefinition = "TEXT")
  private String addressDetail;

  @ManyToOne
  @JoinColumn(name = "address_id", referencedColumnName = "id")
  private Address address;

  @Column(columnDefinition = "TEXT")
  private String description;

  private String taxCode;
  private Integer levelPoint;
  private String source;
  private Long sourceId;

  @Column(nullable = false)
  private Integer status = Status.ACTIVE;

  @Column(nullable = false)
  private Integer customerTypeId;

  @OneToMany(mappedBy = "customer")
  private Set<CampaignCustomer> campaignCustomers;

  @OneToMany(mappedBy = "customer")
  private Set<CustomerUser> customerUsers;

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

  public String getAddressDetail() {
    return addressDetail;
  }

  public void setAddressDetail(String addressDetail) {
    this.addressDetail = addressDetail;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
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

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Long getSourceId() {
    return sourceId;
  }

  public void setSourceId(Long sourceId) {
    this.sourceId = sourceId;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<CampaignCustomer> getCampaignCustomers() {
    return campaignCustomers;
  }

  public void setCampaignCustomers(Set<CampaignCustomer> crmCampaignCustomers) {
    this.campaignCustomers = crmCampaignCustomers;
  }

  public Set<CustomerUser> getCustomerUsers() {
    return customerUsers;
  }

  public void setCustomerUsers(Set<CustomerUser> crmCustomerUsers) {
    this.customerUsers = crmCustomerUsers;
  }
}
