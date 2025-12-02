package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.sharedjpa.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_reference")
public class CustomerReference extends BaseEntity {
  private String code; // ZALo, FB, LICENSE_PAPER...

  @Column(columnDefinition = "TEXT")
  private String name;

  @Column(columnDefinition = "TEXT")
  private String value;

  @Column(columnDefinition = "TEXT")
  private String reference;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
  @JsonIgnore
  private Customer customer;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
}
