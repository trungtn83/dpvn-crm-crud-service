package com.dpvn.crmcrudservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerStatusDto {
  private Long owner; // sale id, NOT idf
  private Long occupier;
  private Instant availableTo;
  private Set<Long> relatedOwners = new HashSet<>();
  private String status; // ore, rom or gangue

  public Long getOwner() {
    return owner;
  }

  public void setOwner(Long owner) {
    this.owner = owner;
  }

  public Set<Long> getRelatedOwners() {
    return relatedOwners;
  }

  public void setRelatedOwners(Set<Long> relatedOwners) {
    this.relatedOwners = relatedOwners;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getOccupier() {
    return occupier;
  }

  public void setOccupier(Long occupier) {
    this.occupier = occupier;
  }

  public Instant getAvailableTo() {
    return availableTo;
  }

  public void setAvailableTo(Instant availableTo) {
    this.availableTo = availableTo;
  }
}
