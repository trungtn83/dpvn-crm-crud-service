package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.shared.domain.BaseDto;
import com.dpvn.shared.util.ListUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends BaseDto<User> {
  private String username;
  private String password;
  private String fullName;
  private String email;
  private String mobilePhone;
  private Instant dob;
  private String description;
  private Long roleId;
  private Long departmentId;
  private String addressDetail;
  private Long addressId;
  private RoleDto role;
  private DepartmentDto department;
  private List<UserPropertyDto> properties;

  public UserDto() {
    super(User.class);
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

  public RoleDto getRole() {
    return role;
  }

  public void setRole(RoleDto role) {
    this.role = role;
  }

  public DepartmentDto getDepartment() {
    return department;
  }

  public void setDepartment(DepartmentDto department) {
    this.department = department;
  }

  public List<UserPropertyDto> getProperties() {
    return properties;
  }

  public void setProperties(List<UserPropertyDto> properties) {
    this.properties = properties;
  }

  @JsonIgnore
  public List<Long> getJudasMemberIds() {
    if (ListUtil.isEmpty(properties)) {
      return List.of();
    }
    return properties.stream()
        .filter(
            p ->
                "MEMBER".equals(p.getCode())
                    && "JUDAS".equals(p.getStatus())
                    && (p.getFromDate() == null || p.getFromDate().isBefore(Instant.now()))
                    && (p.getToDate() == null || p.getToDate().isAfter(Instant.now())))
        .map(p -> Long.parseLong(p.getValue()))
        .toList();
  }

  @JsonIgnore
  public List<Long> getDiscipleMemberIds() {
    if (ListUtil.isEmpty(properties)) {
      return List.of();
    }
    return properties.stream()
        .filter(p -> "MEMBER".equals(p.getCode()) && "DISCIPLE".equals(p.getStatus()))
        .map(p -> Long.parseLong(p.getValue()))
        .toList();
  }
}
