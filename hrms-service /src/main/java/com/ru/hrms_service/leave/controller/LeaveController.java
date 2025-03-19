package com.ru.hrms_service.leave.controller;

import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.leave.models.request.ApplyLeaveRequest;
import com.ru.hrms_service.leave.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/hrms/leave")
@RequiredArgsConstructor
public class LeaveController {


    public static final String ENDPOINT_FETCH_LEAVE_TYPE = "/fetch-leaveType";
    public static final String ENDPIONT_APPLY_LEAVE = "apply-leave";
    private  final LeaveService leaveService;

  @GetMapping(ENDPOINT_FETCH_LEAVE_TYPE)
  public ResponseEntity<ApiResponse> fetchLeaveType(){
      return  ResponseEntity.ok(leaveService.fetchLeaveType());
  }

   @PostMapping(ENDPIONT_APPLY_LEAVE)
   public void applyLeave(@Valid @RequestBody ApplyLeaveRequest applyLeaveRequest,
                          @RequestParam("userId") Long loggedInUserId){
      leaveService.applyLeave(applyLeaveRequest,loggedInUserId);
   }


}
