package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.BaseEntity;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.Status;
import com.dpvn.crmcrudservice.domain.constant.RelationshipType;
import com.dpvn.crmcrudservice.domain.dto.UserCustomerDto;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_customer")
public class UserCustomer extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;
  private Long customerId;
  private int relationshipType = RelationshipType.PIC;
  private int status = Status.ACTIVE;
  private Instant assignedDate;
  private int lifetime = 99;

  private int customerStatus;

  @Column(columnDefinition = "TEXT")
  private String customerStatusNote;

  // finance info
  private String bankAccount;

  @Column(columnDefinition = "TEXT")
  private String bankName;

  private Long creditLimit;

  @Column(columnDefinition = "TEXT")
  private String creditLimitNote;

  @Column(columnDefinition = "TEXT")
  private String paymentHistory;

  // transactions, move to table: Interaction and Issue

  // characteristics - preferences
  private Boolean priceMarkedUp;
  private Integer priceMarkedUpPercentage;

  @Column(columnDefinition = "TEXT")
  private String priceMarkedUpReason;

  @Column(columnDefinition = "TEXT")
  private String customerDifficulty;

  @Column(columnDefinition = "TEXT")
  private String customerDifficultyReason;

  @Column(columnDefinition = "TEXT")
  private String preferredProducts;

  @Column(columnDefinition = "TEXT")
  private String specialRequirements;

  @Column(columnDefinition = "TEXT")
  private String customerPotential;

  @Column(columnDefinition = "TEXT")
  private String potentialProducts;

  private Integer promotionInterestLevel;

  @Column(columnDefinition = "TEXT")
  private String lastPromotions;

  @Column(columnDefinition = "TEXT")
  private String preferredContactTime;

  @Column(columnDefinition = "TEXT")
  private String preferredContactType;

  @Column(columnDefinition = "TEXT")
  private String purchaseMotivation;

  private String purchaseBarriers;

  // Customer Lifetime Value - CLV
  @Column(columnDefinition = "TEXT")
  private String orderFrequency;

  private Instant orderLastDate;
  private Long lifetimeValue;
  private Long avgOrderValue;
  private Long maxOrderValue;
  private Long minOrderValue;
  private Long acquisitionCost; // cost to make become customer from lead

  // Churn Risk
  private Long churnRiskScore; // 1 - 10

  @Column(columnDefinition = "TEXT")
  private String churnReason;

  @Override
  public UserCustomerDto toDto() {
    return BeanMapper.instance().map(this, UserCustomerDto.class);
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

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public int getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(int relationshipType) {
    this.relationshipType = relationshipType;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Instant getAssignedDate() {
    return assignedDate;
  }

  public void setAssignedDate(Instant assignedDate) {
    this.assignedDate = assignedDate;
  }

  public int getLifetime() {
    return lifetime;
  }

  public void setLifetime(int lifetime) {
    this.lifetime = lifetime;
  }

  public int getCustomerStatus() {
    return customerStatus;
  }

  public void setCustomerStatus(int customerStatus) {
    this.customerStatus = customerStatus;
  }

  public String getCustomerStatusNote() {
    return customerStatusNote;
  }

  public void setCustomerStatusNote(String customerStatusNote) {
    this.customerStatusNote = customerStatusNote;
  }

  public String getBankAccount() {
    return bankAccount;
  }

  public void setBankAccount(String bankAccount) {
    this.bankAccount = bankAccount;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public Long getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(Long creditLimit) {
    this.creditLimit = creditLimit;
  }

  public String getCreditLimitNote() {
    return creditLimitNote;
  }

  public void setCreditLimitNote(String creditLimitNote) {
    this.creditLimitNote = creditLimitNote;
  }

  public String getPaymentHistory() {
    return paymentHistory;
  }

  public void setPaymentHistory(String paymentHistory) {
    this.paymentHistory = paymentHistory;
  }

  public Boolean getPriceMarkedUp() {
    return priceMarkedUp;
  }

  public void setPriceMarkedUp(Boolean priceMarkedUp) {
    this.priceMarkedUp = priceMarkedUp;
  }

  public Integer getPriceMarkedUpPercentage() {
    return priceMarkedUpPercentage;
  }

  public void setPriceMarkedUpPercentage(Integer priceMarkedUpPercentage) {
    this.priceMarkedUpPercentage = priceMarkedUpPercentage;
  }

  public String getPriceMarkedUpReason() {
    return priceMarkedUpReason;
  }

  public void setPriceMarkedUpReason(String priceMarkedUpReason) {
    this.priceMarkedUpReason = priceMarkedUpReason;
  }

  public String getCustomerDifficulty() {
    return customerDifficulty;
  }

  public void setCustomerDifficulty(String customerDifficulty) {
    this.customerDifficulty = customerDifficulty;
  }

  public String getCustomerDifficultyReason() {
    return customerDifficultyReason;
  }

  public void setCustomerDifficultyReason(String customerDifficultyReason) {
    this.customerDifficultyReason = customerDifficultyReason;
  }

  public String getPreferredProducts() {
    return preferredProducts;
  }

  public void setPreferredProducts(String preferredProducts) {
    this.preferredProducts = preferredProducts;
  }

  public String getSpecialRequirements() {
    return specialRequirements;
  }

  public void setSpecialRequirements(String specialRequirements) {
    this.specialRequirements = specialRequirements;
  }

  public String getCustomerPotential() {
    return customerPotential;
  }

  public void setCustomerPotential(String customerPotential) {
    this.customerPotential = customerPotential;
  }

  public String getPotentialProducts() {
    return potentialProducts;
  }

  public void setPotentialProducts(String potentialProducts) {
    this.potentialProducts = potentialProducts;
  }

  public Integer getPromotionInterestLevel() {
    return promotionInterestLevel;
  }

  public void setPromotionInterestLevel(Integer promotionInterestLevel) {
    this.promotionInterestLevel = promotionInterestLevel;
  }

  public String getLastPromotions() {
    return lastPromotions;
  }

  public void setLastPromotions(String lastPromotions) {
    this.lastPromotions = lastPromotions;
  }

  public String getPreferredContactTime() {
    return preferredContactTime;
  }

  public void setPreferredContactTime(String preferredContactTime) {
    this.preferredContactTime = preferredContactTime;
  }

  public String getPreferredContactType() {
    return preferredContactType;
  }

  public void setPreferredContactType(String preferredContactType) {
    this.preferredContactType = preferredContactType;
  }

  public String getPurchaseMotivation() {
    return purchaseMotivation;
  }

  public void setPurchaseMotivation(String purchaseMotivation) {
    this.purchaseMotivation = purchaseMotivation;
  }

  public String getPurchaseBarriers() {
    return purchaseBarriers;
  }

  public void setPurchaseBarriers(String purchaseBarriers) {
    this.purchaseBarriers = purchaseBarriers;
  }

  public String getOrderFrequency() {
    return orderFrequency;
  }

  public void setOrderFrequency(String orderFrequency) {
    this.orderFrequency = orderFrequency;
  }

  public Instant getOrderLastDate() {
    return orderLastDate;
  }

  public void setOrderLastDate(Instant orderLastDate) {
    this.orderLastDate = orderLastDate;
  }

  public Long getLifetimeValue() {
    return lifetimeValue;
  }

  public void setLifetimeValue(Long lifetimeValue) {
    this.lifetimeValue = lifetimeValue;
  }

  public Long getAvgOrderValue() {
    return avgOrderValue;
  }

  public void setAvgOrderValue(Long avgOrderValue) {
    this.avgOrderValue = avgOrderValue;
  }

  public Long getMaxOrderValue() {
    return maxOrderValue;
  }

  public void setMaxOrderValue(Long maxOrderValue) {
    this.maxOrderValue = maxOrderValue;
  }

  public Long getMinOrderValue() {
    return minOrderValue;
  }

  public void setMinOrderValue(Long minOrderValue) {
    this.minOrderValue = minOrderValue;
  }

  public Long getAcquisitionCost() {
    return acquisitionCost;
  }

  public void setAcquisitionCost(Long acquisitionCost) {
    this.acquisitionCost = acquisitionCost;
  }

  public Long getChurnRiskScore() {
    return churnRiskScore;
  }

  public void setChurnRiskScore(Long churnRiskScore) {
    this.churnRiskScore = churnRiskScore;
  }

  public String getChurnReason() {
    return churnReason;
  }

  public void setChurnReason(String churnReason) {
    this.churnReason = churnReason;
  }
}
