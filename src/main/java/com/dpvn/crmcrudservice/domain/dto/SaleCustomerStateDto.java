package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.constant.SaleCustomers;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomerState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleCustomerStateDto extends BaseDto {
  private Long saleId;
  private Long customerId;
  private Long customerCategoryId;
  private Integer status = SaleCustomers.State.NEW_CONTACT;
  private String feeShip;
  private String priceMakeUp;
  private String preferProducts;
  private String preferProductsPrice;
  private String characteristic;
  // TODO: more in the future if need
  private String other; // general information

  @Override
  public SaleCustomerState toEntity() {
    return BeanMapper.instance().map(this, SaleCustomerState.class);
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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
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
