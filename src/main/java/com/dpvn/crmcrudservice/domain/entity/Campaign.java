package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.CampaignDto;
import com.dpvn.crmcrudservice.domain.entity.relationship.CampaignCustomer;
import com.dpvn.crmcrudservice.domain.entity.relationship.CampaignUser;
import com.dpvn.shared.domain.BaseEntity;
import com.dpvn.shared.domain.BeanMapper;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "campaign")
public class Campaign extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String campaignName;

  @Column(columnDefinition = "TEXT")
  private String description;

  private Long campaignTypeId;

  @Column(nullable = false)
  private Instant startDate;

  @Column(nullable = false)
  private Instant endDate;

  private Integer steps;
  private Integer dispatchTypeId;

  @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CampaignStep> campaignSteps;

  @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CampaignCustomer> campaignCustomers;

  @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CampaignUser> campaignUsers;

  @Override
  public CampaignDto toDto() {
    return BeanMapper.instance().map(this, CampaignDto.class);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCampaignName() {
    return campaignName;
  }

  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getCampaignTypeId() {
    return campaignTypeId;
  }

  public void setCampaignTypeId(Long campaignTypeId) {
    this.campaignTypeId = campaignTypeId;
  }

  public Instant getStartDate() {
    return startDate;
  }

  public void setStartDate(Instant startDate) {
    this.startDate = startDate;
  }

  public Instant getEndDate() {
    return endDate;
  }

  public void setEndDate(Instant endDate) {
    this.endDate = endDate;
  }

  public Integer getSteps() {
    return steps;
  }

  public void setSteps(Integer steps) {
    this.steps = steps;
  }

  public Set<CampaignStep> getCampaignSteps() {
    return campaignSteps;
  }

  public void setCampaignSteps(Set<CampaignStep> campaignSteps) {
    this.campaignSteps = campaignSteps;
  }

  public Set<CampaignCustomer> getCampaignCustomers() {
    return campaignCustomers;
  }

  public void setCampaignCustomers(Set<CampaignCustomer> campaignCustomers) {
    this.campaignCustomers = campaignCustomers;
  }

  public Set<CampaignUser> getCampaignUsers() {
    return campaignUsers;
  }

  public void setCampaignUsers(Set<CampaignUser> crmCampaignUsers) {
    this.campaignUsers = crmCampaignUsers;
  }

  public Integer getDispatchTypeId() {
    return dispatchTypeId;
  }
}
