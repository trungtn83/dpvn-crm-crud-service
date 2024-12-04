package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.BaseEntity;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.constant.Status;
import com.dpvn.crmcrudservice.domain.dto.AddressDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String level;

  @Column(unique = true, nullable = false)
  private String wardCode;

  @Column(nullable = false)
  private String wardName;

  @Column(nullable = false)
  private String districtCode;

  @Column(nullable = false)
  private String districtName;

  @Column(nullable = false)
  private String provinceCode;

  @Column(nullable = false)
  private String provinceName;

  @Column(nullable = false)
  private String regionCode;

  @Column(nullable = false)
  private String regionName;

  private Integer status = Status.ACTIVE;
  private Double feeRatio;

  @Override
  public AddressDto toDto() {
    return BeanMapper.instance().map(this, AddressDto.class);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Double getFeeRatio() {
    return feeRatio;
  }

  public void setFeeRatio(Double feeRatio) {
    this.feeRatio = feeRatio;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }
}
