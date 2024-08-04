package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.BaseEntity;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.Status;
import com.dpvn.crmcrudservice.domain.dto.CustomerLevelDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "customer_level")
public class CustomerLevel extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String customerLevelName;

  @Column(columnDefinition = "TEXT")
  private String description;

  private Instant startDate;
  private Instant endDate;

  @Column(nullable = false)
  private Integer status = Status.ACTIVE;

  @OneToMany(mappedBy = "customerLevel", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CustomerLevelDetail> customerLevelDetails;

  @Override
  public CustomerLevelDto toDto() {
    return BeanMapper.instance().map(this, CustomerLevelDto.class);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCustomerLevelName() {
    return customerLevelName;
  }

  public void setCustomerLevelName(String customerLevelName) {
    this.customerLevelName = customerLevelName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Instant getStartDate() {
    return startDate;
  }

  public void setStartDate(Instant startDate) {
    this.startDate = startDate;
  }

  public Instant getEndDate() {
    return endDate;
  }

  public void setEndDate(Instant endDate) {
    this.endDate = endDate;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Set<CustomerLevelDetail> getCustomerLevelDetails() {
    return customerLevelDetails;
  }

  public void setCustomerLevelDetails(Set<CustomerLevelDetail> customerLevelDetails) {
    this.customerLevelDetails = customerLevelDetails;
  }
}
