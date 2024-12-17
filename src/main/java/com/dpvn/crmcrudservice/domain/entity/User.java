package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.DepartmentDto;
import com.dpvn.crmcrudservice.domain.dto.RoleDto;
import com.dpvn.crmcrudservice.domain.dto.UserDto;
import com.dpvn.shared.domain.BaseEntity;
import com.dpvn.shared.domain.BeanMapper;
import com.dpvn.shared.domain.entity.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "`user`")
public class User extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  private String password;
  private String fullName;

  private String email;

  private String mobilePhone;

  @Column(name = "dob", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private Instant dob;

  @Column(columnDefinition = "TEXT")
  private String description;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
  private Role role;

  @ManyToOne
  @JoinColumn(name = "department_id", nullable = false, referencedColumnName = "id")
  private Department department;

  @Column(columnDefinition = "TEXT")
  private String addressDetail;

  @ManyToOne
  @JoinColumn(name = "address_id", referencedColumnName = "id")
  private Address address;

  @Override
  public UserDto toDto() {
    UserDto dto = BeanMapper.instance().map(this, UserDto.class);
    dto.setRoleDto(BeanMapper.instance().map(this.role, RoleDto.class));
    dto.setDepartmentDto(BeanMapper.instance().map(this.department, DepartmentDto.class));
    return dto;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Instant getDob() {
    return dob;
  }

  public void setDob(Instant dob) {
    this.dob = dob;
  }
}
