package com.ru.hrms_service.leave.controller;

import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.leave.models.request.ApplyLeaveRequest;
import com.ru.hrms_service.leave.models.request.FetchLeaveRequest;
import com.ru.hrms_service.leave.models.request.UpdateLeaveStatusRequest;
import com.ru.hrms_service.leave.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/hrms/leave")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LeaveController {

    private  final LeaveService leaveService;

  @GetMapping("/fetch-leaveType")
  public ResponseEntity<ApiResponse> fetchLeaveType(){
      return  ResponseEntity.ok(leaveService.fetchLeaveType());
  }

   @PostMapping("/apply-leave")
   public ResponseEntity<ApiResponse> applyLeave(@Valid @RequestBody ApplyLeaveRequest applyLeaveRequest,
                                                 @RequestParam("userId") Long loggedInUserId){
    return ResponseEntity.ok(leaveService.applyLeave(applyLeaveRequest,loggedInUserId));
   }

   @PutMapping("/update-leave-status")
   public void updateLeaveRequestStatus(@RequestBody UpdateLeaveStatusRequest updateLeaveStatusRequest,
                                        @RequestParam("userId") Long userId){

      leaveService.updateLeaveStatus(updateLeaveStatusRequest, userId);

   }

   @PostMapping("/leave-request-details")
   public ResponseEntity<ApiResponse> leaveRequestDetail(@RequestBody FetchLeaveRequest fetchLeaveRequest,
                                                         @RequestHeader("userId") Long loggedInUserId){
         return ResponseEntity.ok(this.leaveService.fetchLeaves(fetchLeaveRequest, loggedInUserId));

   }
}
