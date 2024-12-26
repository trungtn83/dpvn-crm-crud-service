package com.dpvn.crmcrudservice.hrm.leaverequest;

import com.dpvn.crmcrudservice.domain.dto.LeaveRequestDto;
import com.dpvn.crmcrudservice.domain.entity.LeaveRequest;
import com.dpvn.shared.controller.AbstractCrudController;
import com.dpvn.shared.util.DateUtil;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hrm/leave-request")
public class LeaveRequestController extends AbstractCrudController<LeaveRequest, LeaveRequestDto> {
  public LeaveRequestController(LeaveRequestService leaveRequestService) {
    super(leaveRequestService);
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
