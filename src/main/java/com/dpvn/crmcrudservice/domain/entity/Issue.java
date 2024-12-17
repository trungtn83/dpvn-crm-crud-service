package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.IssueDto;
import com.dpvn.shared.domain.BaseEntity;
import com.dpvn.shared.domain.BeanMapper;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "issue")
public class Issue extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long issueTypeId;
  private String issueCode;

  @Column(columnDefinition = "TEXT")
  private String issueTitle;

  @Column(columnDefinition = "TEXT")
  private String issueDescription;

  private Long handleBy;
  private Instant handleDate;
  private Instant resolutionDate;
  private Integer statusId;
  private String statusName;

  @Override
  public IssueDto toDto() {
    return BeanMapper.instance().map(this, IssueDto.class);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
