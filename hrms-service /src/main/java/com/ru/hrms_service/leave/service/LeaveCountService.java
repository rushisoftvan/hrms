package com.ru.hrms_service.leave.service;

import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.leave.entities.UserLeaveCountEntity;
import com.ru.hrms_service.leave.projection.LeaveCountDetailProjection;
import com.ru.hrms_service.leave.repositories.UserLeveCountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveCountService {


    private final UserLeveCountRepo userLeveCountRepo;

    public void fetchLeaveCountDetails(Long userId){
        List<LeaveCountDetailProjection> leaveCountDetailProjections = userLeveCountRepo.fetchUserLeaveCountDetail(userId);
        ApiResponse.success(leaveCountDetailProjections, "fetch successfully");
    }

}
