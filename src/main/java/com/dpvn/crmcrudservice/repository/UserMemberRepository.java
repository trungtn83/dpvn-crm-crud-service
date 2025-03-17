package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.UserMember;
import com.dpvn.shared.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMemberRepository extends AbstractRepository<UserMember> {
  UserMember findByLeaderIdAndMemberId(Long leaderId, Long memberId);
}
