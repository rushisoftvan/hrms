package com.ru.hrms_service.leave.service;

import com.ru.hrms_service.leave.repositories.LeaveTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveTypeRepo leaveTypeRepo;



}
