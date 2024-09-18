package com.dpvn.crmcrudservice.hrm.leaverequest;

import com.dpvn.crmcrudservice.AbstractService;
import com.dpvn.crmcrudservice.domain.entity.LeaveRequest;
import com.dpvn.crmcrudservice.repository.LeaveRequestRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LeaveRequestService extends AbstractService<LeaveRequest> {
  public LeaveRequestService(LeaveRequestRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<LeaveRequest> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public List<LeaveRequest> findByUserId(Long userId) {
    return ((LeaveRequestRepository) repository).findByUserId(userId);
  }

  public List<LeaveRequest> findByUserIdInAndRequestDateBetween(
      List<Long> userIds, Instant fromDate, Instant toDate) {
    return ((LeaveRequestRepository) repository)
        .findByUserIdInAndRequestDateBetween(userIds, fromDate, toDate);
  }
}
