package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.entity.Issue;
import com.dpvn.shared.domain.BaseDto;
import com.dpvn.shared.domain.BeanMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueDto extends BaseDto {
  private Long issueTypeId;
  private String issueCode;
  private String issueTitle;
  private String issueDescription;
  private Long handleBy;
  private Instant handleDate;
  private Instant resolutionDate;
  private Integer statusId;
  private String statusName;

  @Override
  public Issue toEntity() {
    return BeanMapper.instance().map(this, Issue.class);
  }

  public Long getIssueTypeId() {
    return issueTypeId;
  }

  public void setIssueTypeId(Long issueTypeId) {
    this.issueTypeId = issueTypeId;
  }

  public String getIssueCode() {
    return issueCode;
  }

  public void setIssueCode(String issueCode) {
    this.issueCode = issueCode;
  }

  public String getIssueTitle() {
    return issueTitle;
  }

  public void setIssueTitle(String issueTitle) {
    this.issueTitle = issueTitle;
  }

  public String getIssueDescription() {
    return issueDescription;
  }

  public void setIssueDescription(String issueDescription) {
    this.issueDescription = issueDescription;
  }

  public Long getHandleBy() {
    return handleBy;
  }

  public void setHandleBy(Long handleBy) {
    this.handleBy = handleBy;
  }

  public Instant getHandleDate() {
    return handleDate;
  }

  public void setHandleDate(Instant handleDate) {
    this.handleDate = handleDate;
  }

  public Instant getResolutionDate() {
    return resolutionDate;
  }

  public void setResolutionDate(Instant resolutionDate) {
    this.resolutionDate = resolutionDate;
  }

  public Integer getStatusId() {
    return statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }
}
