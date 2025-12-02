package com.dpvn.crmcrudservice.domain.dto;

public class SandBankCustomerDto extends CustomerDto {
  private Boolean favorite;
  private Boolean boom;
  private Boolean sold;

  public Boolean getFavorite() {
    return favorite;
  }

  public void setFavorite(Boolean favorite) {
    this.favorite = favorite;
  }

  public Boolean getBoom() {
    return boom;
  }

  public void setBoom(Boolean boom) {
    this.boom = boom;
  }

  public Boolean getSold() {
    return sold;
  }

  public void setSold(Boolean sold) {
    this.sold = sold;
  }
}
