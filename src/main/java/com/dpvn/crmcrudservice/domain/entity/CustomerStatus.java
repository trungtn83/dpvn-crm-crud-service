package com.dpvn.crmcrudservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerStatus {
  private SaleCustomer owner;
  private SaleCustomer occupier;
  private Instant availableTo;
  private List<SaleCustomer> relatedOwners = new ArrayList<>();
  private String status; // ore, rom or gangue

  public CustomerStatus() {}

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<SaleCustomer> getRelatedOwners() {
    return relatedOwners;
  }

  public void setRelatedOwners(List<SaleCustomer> relatedOwners) {
    this.relatedOwners = relatedOwners;
  }

  public SaleCustomer getOwner() {
    return owner;
  }

  public void setOwner(SaleCustomer owner) {
    this.owner = owner;
  }

  public SaleCustomer getOccupier() {
    return occupier;
  }

  public void setOccupier(SaleCustomer occupier) {
    this.occupier = occupier;
  }

  public Instant getAvailableTo() {
    return availableTo;
  }

  public void setAvailableTo(Instant availableTo) {
    this.availableTo = availableTo;
  }
}
