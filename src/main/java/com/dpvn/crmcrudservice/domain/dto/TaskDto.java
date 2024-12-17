package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.constant.Tasks;
import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.shared.domain.BaseDto;
import com.dpvn.shared.domain.BeanMapper;
import com.dpvn.shared.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// @JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto extends BaseDto {
  private Long userId;
  private Long customerId;
  private Long campaignId;
  private Long kpiId;
  private Long otherId;

  private Integer priority = Tasks.Priority.LOW;
  private Integer progress = 0;
  private Integer typeId = Tasks.Type.OTHER;
  private Integer workload;
  private String name;
  private String title;
  private String content;
  private Instant fromDate;
  private Instant toDate;
  private List<Long> relatedPersonIds = new ArrayList<>();

  @Override
  public Task toEntity() {
    Task entity = BeanMapper.instance().map(this, Task.class);
    entity.setRelatedPersonIds(
        StringUtil.join(relatedPersonIds.stream().map(String::valueOf).toList()));
    return entity;
  }

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

  public void setProgress(Integer progress) {
    this.progress = progress;
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

  public List<Long> getRelatedPersonIds() {
    return relatedPersonIds;
  }

  public void setRelatedPersonIds(List<Long> relatedPersonIds) {
    this.relatedPersonIds = relatedPersonIds;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }
}
