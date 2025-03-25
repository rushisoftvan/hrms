package com.ru.hrms_service.leave.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hrms/leave-count")
public class LeaveCountDetailController {


    @PostMapping("/fetch-leave-count")
    public void getLeaveCountDetailByUserId(){


    }


}
