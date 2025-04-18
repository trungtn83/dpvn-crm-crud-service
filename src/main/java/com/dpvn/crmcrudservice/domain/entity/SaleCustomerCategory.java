package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.SaleCustomerCategoryDto;
import com.dpvn.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sale_customer_category")
public class SaleCustomerCategory extends BaseEntity<SaleCustomerCategoryDto> {
  private Long saleId;
  private String code;

  @Column(columnDefinition = "TEXT")
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  public SaleCustomerCategory() {
    super(SaleCustomerCategoryDto.class);
  }

  public Long getSaleId() {
    return saleId;
  }

  public void setSaleId(Long saleId) {
    this.saleId = saleId;
  }

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
