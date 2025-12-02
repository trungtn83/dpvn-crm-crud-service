package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.constant.Tasks;
import com.dpvn.sharedjpa.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "task")
public class Task extends BaseEntity {
  private Long userId;
  private Long customerId;
  private Long campaignId;
  private Long kpiId;
  private Long otherId;

  private Integer priority = Tasks.Priority.LOW;
  private Integer progress = 0;
  private Integer typeId = Tasks.Type.OTHER;
  private Integer workload;

  @Column(columnDefinition = "TEXT")
  private String name;

  @Column(columnDefinition = "TEXT")
  private String title;

  @Column(columnDefinition = "TEXT")
  private String content;

  private Instant fromDate;
  private Instant toDate;

  @Column(columnDefinition = "TEXT")
  private String relatedPersonIds;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Long getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(Long campaignId) {
    this.campaignId = campaignId;
  }

  public Long getKpiId() {
    return kpiId;
  }

  public void setKpiId(Long kpiId) {
    this.kpiId = kpiId;
  }

  public Long getOtherId() {
    return otherId;
  }

  public void setOtherId(Long otherId) {
    this.otherId = otherId;
  }

  public Integer getProgress() {
    return progress;
  }

  public void setProgress(Integer progressId) {
    this.progress = progressId;
  }

  public Integer getTypeId() {
    return typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }

  public Integer getWorkload() {
    return workload;
  }

  public void setWorkload(Integer workload) {
    this.workload = workload;
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

  public String getRelatedPersonIds() {
    return relatedPersonIds;
  }

  @JsonSetter("relatedPersonIds")
  public void setRelatedPersonIds(List<Long> relatedPersonIds) {
    this.relatedPersonIds =
        String.join(",", relatedPersonIds.stream().map(String::valueOf).toList());
  }

  public void setRelatedPersonIds(String relatedPersonIds) {
    this.relatedPersonIds = relatedPersonIds;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }
}
