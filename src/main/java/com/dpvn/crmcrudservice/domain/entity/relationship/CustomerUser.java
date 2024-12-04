package com.dpvn.crmcrudservice.domain.entity.relationship;

import com.dpvn.crmcrudservice.domain.BaseEntity;
import com.dpvn.crmcrudservice.domain.constant.Status;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "customer_user")
public class CustomerUser extends BaseEntity {

  @Id
  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private Customer customer;

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Id
  @Column(name = "relationship_type_id")
  private Integer relationshipTypeId;

  @Id
  @Column(name = "status")
  private Integer status = Status.ACTIVE;

  public CustomerUser() {}

  public CustomerUser(
      Customer customer, User user, Integer relationshipTypeId, Instant assignedDate) {
    this.customer = customer;
    this.user = user;
    this.relationshipTypeId = relationshipTypeId;
    this.assignedDate = assignedDate;
    this.status = Status.ACTIVE;
  }

  private Instant assignedDate;

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getRelationshipTypeId() {
    return relationshipTypeId;
  }

  public void setRelationshipTypeId(Integer relationshipTypeId) {
    this.relationshipTypeId = relationshipTypeId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CustomerUser that = (CustomerUser) o;

    if (!Objects.equals(customer, that.customer)) {
      return false;
    }
    if (!Objects.equals(user, that.user)) {
      return false;
    }
    return Objects.equals(relationshipTypeId, that.relationshipTypeId);
  }

  @Override
  public int hashCode() {
    int result = customer != null ? customer.hashCode() : 0;
    result = 31 * result + (user != null ? user.hashCode() : 0);
    result = 31 * result + (relationshipTypeId != null ? relationshipTypeId.hashCode() : 0);
    return result;
  }

  public Instant getAssignedDate() {
    return assignedDate;
  }

  public void setAssignedDate(Instant assignedDate) {
    this.assignedDate = assignedDate;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
