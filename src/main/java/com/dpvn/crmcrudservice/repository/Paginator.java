package com.dpvn.crmcrudservice.repository;

public class
Paginator {
  private String sortBy;
  private String sortDirection;
  private Integer page;
  private Integer size;

  private Paginator() {}

  private Paginator(String sortBy, String sortDirection, Integer page, Integer size) {
    this.sortBy = sortBy;
    this.sortDirection = sortDirection;
    this.page = page;
    this.size = size;
  }

  public static Paginator create() {
    return new Paginator();
  }

  public Paginator sortBy(String sortBy) {
    this.sortBy = sortBy;
    return this;
  }

  public Paginator sortDirection(String sortDirection) {
    this.sortDirection = sortDirection;
    return this;
  }

  public Paginator page(Integer page) {
    this.page = page;
    return this;
  }

  public Paginator size(Integer size) {
    this.size = size;
    return this;
  }

  public String getSortBy() {
    return sortBy;
  }

  public String getSortDirection() {
    return sortDirection;
  }

  public Integer getPage() {
    return page;
  }

  public Integer getSize() {
    return size;
  }
}
