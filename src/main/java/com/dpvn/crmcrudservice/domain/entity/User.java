package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.UserDto;
import com.dpvn.shared.domain.BaseEntity;
import com.dpvn.shared.domain.entity.Address;
import com.dpvn.shared.util.ListUtil;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`user`")
public class User extends BaseEntity<UserDto> {

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

  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<UserProperty> properties = new ArrayList<>();

  public User() {
    super(UserDto.class);
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

  public List<UserProperty> getProperties() {
    return properties;
  }

  public void setProperties(List<UserProperty> properties) {
    this.properties = properties;
  }

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
