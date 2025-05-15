package com.ru.hrms_service.leave.controller;


import com.ru.hrms_service.leave.service.LeaveCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/hrms/leave-count")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LeaveCountDetailController {

    private final LeaveCountService leaveCountService;
    @PostMapping("/fetch-leave-count")
    public void getLeaveCountDetailByUserId(@RequestHeader @RequestParam("userId") Long userId){
          this.leaveCountService.fetchLeaveCountDetails(userId);
    }

}
