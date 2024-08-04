package com.dpvn.crmcrudservice.domain;

import com.dpvn.shared.util.DateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.time.Instant;

public abstract class BaseDto implements Serializable {
  @JsonAlias({"id", "Id"})
  private Long id;

  private Boolean isActive;

  @JsonProperty("isActive")
  public Boolean getActive() {
    return isActive;
  }

  @JsonAlias({"IsActive", "isActive"})
  public void setActive(Boolean active) {
    isActive = active;
  }

  private Boolean isDeleted;

  @JsonProperty("isDeleted")
  public Boolean getDeleted() {
    return isDeleted;
  }

  @JsonAlias({"IsDeleted", "isDeleted"})
  public void setDeleted(Boolean deleted) {
    isDeleted = deleted;
  }

  @JsonDeserialize(using = DateTimeDeserializer.class)
  @JsonAlias({"modifiedDate", "ModifiedDate"})
  private Instant modifiedDate;

  @JsonDeserialize(using = DateTimeDeserializer.class)
  @JsonAlias({"createdDate", "CreatedDate"})
  private Instant createdDate;

  @JsonAlias({"createdBy", "CreatedBy"})
  private Long createdBy;

  @JsonAlias({"modifiedBy", "ModifiedBy"})
  private Long modifiedBy;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Instant getModifiedDate() {
    return modifiedDate != null ? modifiedDate : createdDate;
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

  public <E extends BaseEntity> E toEntity() {
    throw new RuntimeException("Should not be called");
  }
}
