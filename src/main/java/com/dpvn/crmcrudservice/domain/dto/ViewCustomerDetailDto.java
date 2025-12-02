package com.dpvn.crmcrudservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ViewCustomerDetailDto {
  private CustomerDto customer;
  private CustomerStatusDto status;
  private SaleCustomerStateDto state;
  private List<String> errors = new ArrayList<>();

  public ViewCustomerDetailDto() {}

  public ViewCustomerDetailDto(String error) {
    this.errors.add(error);
  }

  public CustomerDto getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }

  public CustomerStatusDto getStatus() {
    return status;
  }

  public void setStatus(CustomerStatusDto status) {
    this.status = status;
  }

  public SaleCustomerStateDto getState() {
    return state;
  }

  public void setState(SaleCustomerStateDto state) {
    this.state = state;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }

  public void addError(String error) {
    this.errors.add(error);
  }
}
