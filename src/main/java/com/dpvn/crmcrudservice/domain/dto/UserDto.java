package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.shared.domain.BaseDto;
import com.dpvn.shared.domain.BeanMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends BaseDto {
  private String username;
  private String password;
  private String fullName;
  private String email;
  private String mobilePhone;
  private Instant dob;
  private String description;
  private Long roleId;
  private RoleDto roleDto;
  private Long departmentId;
  private DepartmentDto departmentDto;
  private String addressDetail;
  private Long addressId;

  @Override
  public User toEntity() {
    return BeanMapper.instance().map(this, User.class);
  }

  public Instant getDob() {
    return dob;
  }

  public void setDob(Instant dob) {
    this.dob = dob;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }


  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public Long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
  }

  public String getAddressDetail() {
    return addressDetail;
  }

  public void setAddressDetail(String addressDetail) {
    this.addressDetail = addressDetail;
  }

  public Long getAddressId() {
    return addressId;
  }

  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }

  public RoleDto getRoleDto() {
    return roleDto;
  }

  public void setRoleDto(RoleDto roleDto) {
    this.roleDto = roleDto;
  }

  public DepartmentDto getDepartmentDto() {
    return departmentDto;
  }

  public void setDepartmentDto(DepartmentDto departmentDto) {
    this.departmentDto = departmentDto;
  }
}
