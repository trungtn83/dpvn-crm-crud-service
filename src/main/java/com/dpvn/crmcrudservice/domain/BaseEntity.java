package com.dpvn.crmcrudservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
  @Column(name = "modified_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private Instant modifiedDate;

  @Column(name = "created_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private Instant createdDate;

  @Column(name = "created_by")
  private Long createdBy;

  @Column(name = "modified_by")
  private Long modifiedBy;

  public Instant getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(Instant modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  public Long getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }

  public Long getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(Long modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public <E extends BaseDto> E toDto() {
    throw new RuntimeException("Should not be called");
  }
}
