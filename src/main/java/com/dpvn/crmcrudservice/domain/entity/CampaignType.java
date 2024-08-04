package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.BaseEntity;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.Status;
import com.dpvn.crmcrudservice.domain.dto.CampaignTypeDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "campaign_type")
public class CampaignType extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String campaignTypeName;

  @Column(columnDefinition = "TEXT")
  private String description;

  private Integer status = Status.ACTIVE;
  private Integer steps;

  @OneToMany(mappedBy = "campaignType", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CampaignTypeStep> campaignTypeSteps;

  @Override
  public CampaignTypeDto toDto() {
    return BeanMapper.instance().map(this, CampaignTypeDto.class);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCampaignTypeName() {
    return campaignTypeName;
  }

  public void setCampaignTypeName(String campaignTypeName) {
    this.campaignTypeName = campaignTypeName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getSteps() {
    return steps;
  }

  public void setSteps(Integer steps) {
    this.steps = steps;
  }

  public Set<CampaignTypeStep> getCampaignTypeSteps() {
    return campaignTypeSteps;
  }

  public void setCampaignTypeSteps(Set<CampaignTypeStep> campaignTypeSteps) {
    this.campaignTypeSteps = campaignTypeSteps;
  }
}
