package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.constant.SaleCustomers;
import com.dpvn.crmcrudservice.domain.dto.SaleCustomerStateDto;
import com.dpvn.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/** EXTEND MORE IN THE FUTURE */
@Entity
@Table(name = "sale_customer_state")
public class SaleCustomerState extends BaseEntity<SaleCustomerStateDto> {

  private Long saleId;
  private Long customerId;
  private Long customerCategoryId;

  private Integer state = SaleCustomers.State.NEW_CONTACT;

  @Column(columnDefinition = "TEXT")
  private String feeShip;

  @Column(columnDefinition = "TEXT")
  private String priceMakeUp;

  @Column(columnDefinition = "TEXT")
  private String preferProducts;

  @Column(columnDefinition = "TEXT")
  private String preferProductsPrice;

  @Column(columnDefinition = "TEXT")
  private String characteristic;

  @Column(columnDefinition = "TEXT")
  private String other; // general information

  @Column(columnDefinition = "TEXT")
  private String note;

  public SaleCustomerState() {
    super(SaleCustomerStateDto.class);
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

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
