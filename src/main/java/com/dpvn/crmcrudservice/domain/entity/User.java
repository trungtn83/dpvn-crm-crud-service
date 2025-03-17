package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.UserDto;
import com.dpvn.shared.domain.BaseEntity;
import com.dpvn.shared.domain.BeanMapper;
import com.dpvn.shared.domain.entity.Address;
import com.dpvn.shared.util.ListUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

  @ManyToMany(mappedBy = "members")
  @JsonBackReference
  private List<User> leaders = new ArrayList<>();

  @ManyToMany
  @JoinTable(
      name = "user_member",
      joinColumns = @JoinColumn(name = "member_id"),
      inverseJoinColumns = @JoinColumn(name = "leader_id"))
  @JsonManagedReference
  private List<User> members = new ArrayList<>();

  /**
   * to avoid loop infinitive
   *  - manual remove leader in member list
   *  - manual remove member in leader list
   */
  @Override
  public UserDto toDto() {
    UserDto dto = BeanMapper.instance().map(this, UserDto.class);
    if (ListUtil.isNotEmpty(leaders)) {
      List<UserDto> leaderDtos =
          leaders.stream()
              .map(
                  leader -> {
                    UserDto leaderDto = BeanMapper.instance().map(leader, UserDto.class);
                    leaderDto.setMembers(null);
                    return leaderDto;
                  })
              .toList();
      dto.setLeaders(leaderDtos);
    }
    if (ListUtil.isNotEmpty(members)) {
      List<UserDto> memberDtos =
          members.stream()
              .map(
                  member -> {
                    UserDto memberDto = BeanMapper.instance().map(member, UserDto.class);
                    memberDto.setLeaders(null);
                    return memberDto;
                  })
              .toList();
      dto.setMembers(memberDtos);
    }
    return dto;
  }

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

  public List<User> getLeaders() {
    return leaders;
  }

  public void setLeaders(List<User> leaders) {
    this.leaders = leaders;
  }

  public List<User> getMembers() {
    return members;
  }

  public void setMembers(List<User> members) {
    this.members = members;
  }
}
