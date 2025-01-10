package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.CustomerAddressDto;
import com.dpvn.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_address")
public class CustomerAddress extends BaseEntity<CustomerAddressDto> {
  @Column(columnDefinition = "TEXT")
  private String address;

  private String wardCode;

  @Column(columnDefinition = "TEXT")
  private String wardName;

  private String districtCode;

  @Column(columnDefinition = "TEXT")
  private String districtName;

  private String provinceCode;

  @Column(columnDefinition = "TEXT")
  private String provinceName;

  private String regionCode;

  @Column(columnDefinition = "TEXT")
  private String regionName;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
  private Customer customer;

  public CustomerAddress() {
    super(CustomerAddressDto.class);
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getWardCode() {
    return wardCode;
  }

  public void setWardCode(String wardCode) {
    this.wardCode = wardCode;
  }

  public String getWardName() {
    return wardName;
  }

  public void setWardName(String wardName) {
    this.wardName = wardName;
  }

  public String getDistrictCode() {
    return districtCode;
  }

  public void setDistrictCode(String districtCode) {
    this.districtCode = districtCode;
  }

  public String getDistrictName() {
    return districtName;
  }

  public void setDistrictName(String districtName) {
    this.districtName = districtName;
  }

  public String getProvinceCode() {
    return provinceCode;
  }

  public void setProvinceCode(String provinceCode) {
    this.provinceCode = provinceCode;
  }

  public String getProvinceName() {
    return provinceName;
  }

  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
}
