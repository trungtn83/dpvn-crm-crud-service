package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.sharedjpa.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_type")
public class CustomerType extends BaseEntity {
  private String typeCode;

  @Column(columnDefinition = "TEXT")
  private String typeName;

  @Column(columnDefinition = "TEXT")
  private String typeReferences;

  @Column(columnDefinition = "TEXT")
  private String description;

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

  public String getTypeReferences() {
    return typeReferences;
  }

  public void setTypeReferences(String typeReferences) {
    this.typeReferences = typeReferences;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
