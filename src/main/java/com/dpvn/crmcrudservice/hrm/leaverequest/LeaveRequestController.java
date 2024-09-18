package com.dpvn.crmcrudservice.hrm.leaverequest;

import com.dpvn.crmcrudservice.AbstractController;
import com.dpvn.crmcrudservice.domain.Status;
import com.dpvn.crmcrudservice.domain.dto.LeaveRequestDto;
import com.dpvn.crmcrudservice.domain.entity.LeaveRequest;
import com.dpvn.shared.util.DateUtil;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hrm/leave-request")
public class LeaveRequestController extends AbstractController<LeaveRequest, LeaveRequestDto> {
  public LeaveRequestController(LeaveRequestService leaveRequestService) {
    super(leaveRequestService);
  }

  @Override
  @PostMapping
  public ResponseEntity<LeaveRequestDto> create(@RequestBody LeaveRequestDto dto) {
    LeaveRequest entity = dto.toEntity();
    entity.setCreatedDate(DateUtil.now());
    entity.setStatus(Status.ACTIVE);
    entity.setProgressStatus(0);
    return ResponseEntity.ok(service.save(entity).toDto());
  }

  @GetMapping("/find-by-user/{userId}")
  public List<LeaveRequestDto> findLeaveRequestByUserId(
      @PathVariable(name = "userId") Long userId) {
    return ((LeaveRequestService) service)
        .findByUserId(userId).stream().map(LeaveRequest::toDto).toList();
  }

  @GetMapping("/find-by-users")
  public List<LeaveRequestDto> findLeaveRequestByUsersAndInMonthOfDate(
      @RequestParam List<Long> userIds,
      @RequestParam String fromDate,
      @RequestParam String toDate) {
    return ((LeaveRequestService) service)
            .findByUserIdInAndRequestDateBetween(
                userIds, DateUtil.from(fromDate), DateUtil.from(toDate))
            .stream()
            .map(LeaveRequest::toDto)
            .toList();
  }
}
