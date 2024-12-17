package com.dpvn.crmcrudservice.domain.entity.relationship;

import com.dpvn.crmcrudservice.domain.entity.Campaign;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.shared.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "campaign_user")
public class CampaignUser extends BaseEntity {

  @Id
  @ManyToOne
  @JoinColumn(name = "campaign_id", referencedColumnName = "id")
  private Campaign campaign;

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public CampaignUser(Campaign campaign, User user) {
    this.campaign = campaign;
    this.user = user;
  }

  public CampaignUser() {}

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Campaign getCampaign() {
    return campaign;
  }

  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CampaignUser that = (CampaignUser) o;

    if (!Objects.equals(user, that.user)) {
      return false;
    }
    return Objects.equals(campaign, that.campaign);
  }

  @Override
  public int hashCode() {
    int result = user != null ? user.hashCode() : 0;
    result = 31 * result + (campaign != null ? campaign.hashCode() : 0);
    return result;
  }
}
