package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.Status;
import com.dpvn.crmcrudservice.domain.entity.Department;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDto extends BaseDto {
  private String departmentName;

  private String description;
  private Integer status = Status.ACTIVE;

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
