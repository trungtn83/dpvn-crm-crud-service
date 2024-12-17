package com.dpvn.crmcrudservice.domain.entity.relationship;

import com.dpvn.crmcrudservice.domain.entity.Campaign;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "campaign_customer")
public class CampaignCustomer extends BaseEntity {

  @Id
  @ManyToOne
  @JoinColumn(name = "campaign_id", referencedColumnName = "id")
  private Campaign campaign;

  @Id
  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private Customer customer;

  @Id
  @Column(name = "progress_id")
  private Integer progressId;

  public CampaignCustomer() {}

  public CampaignCustomer(Campaign campaign, Customer customer) {
    this.campaign = campaign;
    this.customer = customer;
    this.progressId = 0;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Campaign getCampaign() {
    return campaign;
  }

  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
  }

  public Integer getProgressId() {
    return progressId;
  }

  public void setProgressId(Integer progressId) {
    this.progressId = progressId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CampaignCustomer that = (CampaignCustomer) o;

    if (!Objects.equals(customer, that.customer)) {
      return false;
    }
    if (!Objects.equals(campaign, that.campaign)) {
      return false;
    }
    return Objects.equals(progressId, that.progressId);
  }

  @Override
  public int hashCode() {
    int result = customer != null ? customer.hashCode() : 0;
    result = 31 * result + (campaign != null ? campaign.hashCode() : 0);
    result = 31 * result + (progressId != null ? progressId.hashCode() : 0);
    return result;
  }
}
