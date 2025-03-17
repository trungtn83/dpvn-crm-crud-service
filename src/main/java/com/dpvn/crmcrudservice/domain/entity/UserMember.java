package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.shared.domain.BaseEntity;
import com.dpvn.shared.domain.VoidDto;
import jakarta.persistence.*;

@Entity
@Table(
    name = "user_member",
    uniqueConstraints = @UniqueConstraint(columnNames = {"leader_id", "member_id"}))
public class UserMember extends BaseEntity<VoidDto> {
  public UserMember() {
    super(VoidDto.class);
  }

  @ManyToOne
  @JoinColumn(name = "leader_id")
  private User leader;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private User member;

  public User getLeader() {
    return leader;
  }

  public void setLeader(User leader) {
    this.leader = leader;
  }

  public User getMember() {
    return member;
  }

  public void setMember(User member) {
    this.member = member;
  }
}
