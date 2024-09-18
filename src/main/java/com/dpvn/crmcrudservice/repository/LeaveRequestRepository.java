package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.LeaveRequest;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
  List<LeaveRequest> findByUserId(Long userId);

  List<LeaveRequest> findByUserIdInAndRequestDateBetween(
      List<Long> userIds, Instant startDate, Instant endDate);
}
