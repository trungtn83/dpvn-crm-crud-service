package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.entity.LeaveRequest;
import com.dpvn.shared.util.ListUtil;
import com.dpvn.shared.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaveRequestDto extends BaseDto {
  private Long userId;
  private Long reasonId;
  private Instant requestDate;
  private Integer requestHours;
  private String notes;
  private List<Long> notifiedUsers;
  private Integer status;
  private Integer progressStatus;
  private Long approvalBy;
  private Instant approvalDate;

  @Override
  public LeaveRequest toEntity() {
    LeaveRequest leaveRequest = BeanMapper.instance().map(this, LeaveRequest.class);
    if (ListUtil.isNotEmpty(notifiedUsers)) {
      leaveRequest.setNotifiedUsers(
          StringUtil.join(notifiedUsers.stream().map(Object::toString).toList()));
    }
    return leaveRequest;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getReasonId() {
    return reasonId;
  }

  public void setReasonId(Long reasonId) {
    this.reasonId = reasonId;
  }

  public Instant getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(Instant requestDate) {
    this.requestDate = requestDate;
  }

  public Integer getRequestHours() {
    return requestHours;
  }

  public void setRequestHours(Integer requestHours) {
    this.requestHours = requestHours;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public List<Long> getNotifiedUsers() {
    return notifiedUsers;
  }

  public void setNotifiedUsers(List<Long> notifiedUsers) {
    this.notifiedUsers = notifiedUsers;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getProgressStatus() {
    return progressStatus;
  }

  public void setProgressStatus(Integer progressStatus) {
    this.progressStatus = progressStatus;
  }

  public Long getApprovalBy() {
    return approvalBy;
  }

  public void setApprovalBy(Long approvalBy) {
    this.approvalBy = approvalBy;
  }

  public Instant getApprovalDate() {
    return approvalDate;
  }

  public void setApprovalDate(Instant approvalDate) {
    this.approvalDate = approvalDate;
  }
}
