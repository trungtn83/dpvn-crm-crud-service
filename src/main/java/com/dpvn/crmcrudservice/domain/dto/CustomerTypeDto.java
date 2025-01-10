package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.entity.CustomerType;
import com.dpvn.shared.domain.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerTypeDto extends BaseDto<CustomerType> {
  private String typeCode;
  private String typeName;
  // contains list of external code that will be mapped to this type
  private List<String> typeReferences = new ArrayList<>();
  private String description;

  public CustomerTypeDto() {
    super(CustomerType.class);
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public List<String> getTypeReferences() {
    return typeReferences;
  }

  public void setTypeReferences(List<String> typeReferences) {
    this.typeReferences = typeReferences;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
