package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.dto.SaleCustomerDto;
import com.dpvn.shared.domain.BaseEntity;
import com.dpvn.shared.domain.BeanMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "sale_customer")
public class SaleCustomer extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long saleId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
  private Customer customer;

  @Column(name = "relationship_type")
  private Integer relationshipType; // PIC or EVOLVE

  private Integer reasonId; // assign by campaign, by self create, by leader....

  @Column(columnDefinition = "TEXT")
  private String reasonRef;

  @Column(columnDefinition = "TEXT")
  private String reasonNote;

  private Instant availableFrom;
  private Instant availableTo;

  @Column(columnDefinition = "TEXT")
  private String note;

  @Override
  public SaleCustomerDto toDto() {
    SaleCustomerDto dto = BeanMapper.instance().map(this, SaleCustomerDto.class);
    dto.setCustomerId(this.customer.getId());
    dto.setCustomerDto(this.customer.toDto());
    return dto;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Integer getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(Integer relationShipType) {
    this.relationshipType = relationShipType;
  }

  public Integer getReasonId() {
    return reasonId;
  }

  public void setReasonId(Integer reasonId) {
    this.reasonId = reasonId;
  }

  public String getReasonNote() {
    return reasonNote;
  }

  public void setReasonNote(String reasonNote) {
    this.reasonNote = reasonNote;
  }

  public Instant getAvailableFrom() {
    return availableFrom;
  }

  public void setAvailableFrom(Instant availableFrom) {
    this.availableFrom = availableFrom;
  }

  public Instant getAvailableTo() {
    return availableTo;
  }

  public void setAvailableTo(Instant availableTo) {
    this.availableTo = availableTo;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Long getSaleId() {
    return saleId;
  }

  public void setSaleId(Long saleId) {
    this.saleId = saleId;
  }

  public String getReasonRef() {
    return reasonRef;
  }

  public void setReasonRef(String reasonRef) {
    this.reasonRef = reasonRef;
  }
}
