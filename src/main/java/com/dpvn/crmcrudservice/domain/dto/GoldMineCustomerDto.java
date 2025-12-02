package com.dpvn.crmcrudservice.domain.dto;

public class GoldMineCustomerDto extends CustomerDto {
  private Boolean favorite;
  private Boolean boom;

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
}
