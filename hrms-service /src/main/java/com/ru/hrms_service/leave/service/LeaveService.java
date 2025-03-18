package com.ru.hrms_service.leave.service;

import com.ru.hrms_service.common.entities.UserEntity;
import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.common.projection.KeyValue;
import com.ru.hrms_service.leave.entities.LeaveRequestEntity;
import com.ru.hrms_service.leave.entities.LeaveTypeEntity;
import com.ru.hrms_service.leave.enums.LeaveStatusEnum;
import com.ru.hrms_service.leave.exception.ResourceNotFoundException;
import com.ru.hrms_service.leave.models.request.ApplyLeaveRequest;
import com.ru.hrms_service.leave.repositories.LeaveRequestRepo;
import com.ru.hrms_service.leave.repositories.LeaveTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeaveService {

    private final LeaveTypeRepo leaveTypeRepo;

    private final LeaveRequestRepo leaveRequestRepo;

    public ApiResponse fetchLeaveType() {
        List<KeyValue<Long, String>> dropDownLeaveType = this.leaveTypeRepo.fetchRequestType();
        return ApiResponse.success(java.util.Map.of("dropDownLeaveType", dropDownLeaveType), "leave types " +
                                                                                             "fetch  successfully "
        );
    }

    @Transactional
    public ApiResponse applyLeave(ApplyLeaveRequest applyLeaveRequest) {
        var leaveTypeEntity =
                leaveTypeRepo.findLeaveTypeById(applyLeaveRequest.leaveTypeId()).orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));


        LeaveRequestEntity leaveRequestEntity = prepareApplyRequest(applyLeaveRequest, leaveTypeEntity);
        LeaveRequestEntity savedLeaveRequest = this.leaveRequestRepo.save(leaveRequestEntity);
        return ApiResponse.success(Map.of("applyLeaveRequest", savedLeaveRequest.getId()), "leave request apply " +
                                                                                           "successfully");


    }

    private static LeaveRequestEntity prepareApplyRequest(ApplyLeaveRequest applyLeaveRequest, LeaveTypeEntity leaveTypeEntity) {
        LeaveRequestEntity leaveRequestEntity = new LeaveRequestEntity();
        leaveRequestEntity.setLeaveStatusEnum(LeaveStatusEnum.PENDING);
        leaveRequestEntity.setLeaveTypeEntity(leaveTypeEntity);
        leaveRequestEntity.setLeaveReason(applyLeaveRequest.leaveReason());
        leaveRequestEntity.setStartDate(applyLeaveRequest.startDate());
        leaveRequestEntity.setEndDate(applyLeaveRequest.endDate());
        leaveRequestEntity.setUser(new UserEntity(2L));
        leaveRequestEntity.setManager(new UserEntity(3l));
        return leaveRequestEntity;
    }
}
