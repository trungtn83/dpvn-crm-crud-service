package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.constant.SaleCustomers;
import com.dpvn.crmcrudservice.domain.dto.SaleCustomerStateDto;
import com.dpvn.shared.domain.BaseEntity;
import com.dpvn.shared.domain.BeanMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** EXTEND MORE IN THE FUTURE */
@Entity
@Table(name = "sale_customer_state")
public class SaleCustomerState extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long saleId;
  private Long customerId;
  private Long customerCategoryId;

  private Integer state = SaleCustomers.State.NEW_CONTACT;

  private String feeShip;
  private String priceMakeUp;
  private String preferProducts;
  private String preferProductsPrice;
  private String characteristic;
  // TODO: more in the future if need
  private String other; // general information

  @Override
  public SaleCustomerStateDto toDto() {
    return BeanMapper.instance().map(this, SaleCustomerStateDto.class);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSaleId() {
    return saleId;
  }

  public void setSaleId(Long saleId) {
    this.saleId = saleId;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer status) {
    this.state = status;
  }

  public String getFeeShip() {
    return feeShip;
  }

  public void setFeeShip(String feeShip) {
    this.feeShip = feeShip;
  }

  public String getPriceMakeUp() {
    return priceMakeUp;
  }

  public void setPriceMakeUp(String priceMakeUp) {
    this.priceMakeUp = priceMakeUp;
  }

  public String getPreferProducts() {
    return preferProducts;
  }

  public void setPreferProducts(String preferProducts) {
    this.preferProducts = preferProducts;
  }

  public String getPreferProductsPrice() {
    return preferProductsPrice;
  }

  public void setPreferProductsPrice(String preferProductsPrice) {
    this.preferProductsPrice = preferProductsPrice;
  }

  public String getCharacteristic() {
    return characteristic;
  }

  public void setCharacteristic(String characteristic) {
    this.characteristic = characteristic;
  }

  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
  }

  public Long getCustomerCategoryId() {
    return customerCategoryId;
  }

  public void setCustomerCategoryId(Long customerCategoryId) {
    this.customerCategoryId = customerCategoryId;
  }
}
