package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.LeaveRequest;
import com.dpvn.shared.repository.AbstractRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends AbstractRepository<LeaveRequest> {
  List<LeaveRequest> findByUserId(Long userId);

  List<LeaveRequest> findByUserIdInAndRequestDateBetween(
      List<Long> userIds, Instant startDate, Instant endDate);
}
