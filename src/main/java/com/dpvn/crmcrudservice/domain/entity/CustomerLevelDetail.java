package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.BaseEntity;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.dto.CustomerLevelDetailDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_level_detail")
public class CustomerLevelDetail extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "customer_level_id", referencedColumnName = "id")
  private CustomerLevel customerLevel;

  @Column(nullable = false)
  private String levelName;

  @Column(columnDefinition = "TEXT")
  private String description;

  private Integer basePoint;

  @Override
  public CustomerLevelDetailDto toDto() {
    return BeanMapper.instance().map(this, CustomerLevelDetailDto.class);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getBasePoint() {
    return basePoint;
  }

  public void setBasePoint(Integer basePoint) {
    this.basePoint = basePoint;
  }

  public CustomerLevel getCustomerLevel() {
    return customerLevel;
  }

  public void setCustomerLevel(CustomerLevel customerLevel) {
    this.customerLevel = customerLevel;
  }
}
