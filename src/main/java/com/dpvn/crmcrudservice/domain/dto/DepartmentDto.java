package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.entity.Department;
import com.dpvn.shared.domain.BaseDto;
import com.dpvn.shared.domain.BeanMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentDto extends BaseDto {
  private String departmentName;

  private String description;

  private Set<UserDto> userDtos;

  @Override
  public Department toEntity() {
    return BeanMapper.instance().map(this, Department.class);
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
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
