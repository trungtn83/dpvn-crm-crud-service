package com.dpvn.crmcrudservice.domain.entity.report;

import java.time.Instant;

public class TaskBySeller {
  private Long id;
  private Long sellerId;
  private String name;
  private String title;
  private String content;
  private Instant fromDate;
  private Instant toDate;
  private Instant modifiedDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Instant getFromDate() {
    return fromDate;
  }

  public void setFromDate(Instant fromDate) {
    this.fromDate = fromDate;
  }

  public Instant getToDate() {
    return toDate;
  }

  public void setToDate(Instant toDate) {
    this.toDate = toDate;
  }

  public Instant getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(Instant modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public Long getSellerId() {
    return sellerId;
  }

  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }
}
