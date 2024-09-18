package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.BaseEntity;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.dto.LeaveRequestDto;
import com.dpvn.shared.util.StringUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "hrm_leave_request")
public class LeaveRequest extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;
  private Long reasonId;
  private Instant requestDate;
  private Integer requestHours;

  @Column(columnDefinition = "TEXT")
  private String notes;

  @Column(columnDefinition = "TEXT")
  private String notifiedUsers;

  private Integer status;
  private Integer progressStatus;
  private Long approvalBy;
  private Instant approvalDate;

  @Override
  public LeaveRequestDto toDto() {
    LeaveRequestDto leaveRequestDto = BeanMapper.instance().map(this, LeaveRequestDto.class);
    if (StringUtil.isNotEmpty(notifiedUsers)) {
      leaveRequestDto.setNotifiedUsers(
          StringUtil.split(notifiedUsers).stream().map(Long::getLong).toList());
    }
    return leaveRequestDto;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getNotifiedUsers() {
    return notifiedUsers;
  }

  public void setNotifiedUsers(String notifiedUsers) {
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
