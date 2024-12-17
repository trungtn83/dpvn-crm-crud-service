package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.entity.Role;
import com.dpvn.shared.domain.BaseDto;
import com.dpvn.shared.domain.BeanMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto extends BaseDto {
  private String roleName;

  private String description;

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

  public Set<UserDto> getUserDtos() {
    return userDtos;
  }

  public void setUserDtos(Set<UserDto> userDtos) {
    this.userDtos = userDtos;
  }
}
