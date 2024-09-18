package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.entity.Role;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto extends BaseDto {
  private String roleName;

  private String description;
  private Integer status;

  private Set<UserDto> userDtos;

  @Override
  public Role toEntity() {
    return BeanMapper.instance().map(this, Role.class);
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Set<UserDto> getUserDtos() {
    return userDtos;
  }

  public void setUserDtos(Set<UserDto> userDtos) {
    this.userDtos = userDtos;
  }
}
