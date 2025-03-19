package com.ru.hrms_service.leave.service;

import com.ru.hrms_service.common.entities.UserEntity;
import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.common.projection.KeyValue;
import com.ru.hrms_service.common.repositories.UserRepo;
import com.ru.hrms_service.holiday.exception.HrmsException;
import com.ru.hrms_service.leave.entities.LeaveRequestEntity;
import com.ru.hrms_service.leave.entities.LeaveTypeEntity;
import com.ru.hrms_service.leave.entities.UserReportingDetailEntity;
import com.ru.hrms_service.leave.enums.LeaveStatusEnum;
import com.ru.hrms_service.leave.exception.LeaveNotAvailableException;
import com.ru.hrms_service.leave.exception.ResourceNotFoundException;
import com.ru.hrms_service.leave.models.request.ApplyLeaveRequest;
import com.ru.hrms_service.leave.repositories.LeaveRequestRepo;
import com.ru.hrms_service.leave.repositories.LeaveTypeRepo;
import com.ru.hrms_service.leave.repositories.UserLeveCountRepo;
import com.ru.hrms_service.leave.repositories.UserReportingDetailRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LeaveService {

    private final LeaveTypeRepo leaveTypeRepo;

    private final LeaveRequestRepo leaveRequestRepo;

    private final UserLeveCountRepo userLeveCountRepo;

    private final UserRepo userRepo;
    private final UserReportingDetailRepo userReportingDetailRepo;

    public ApiResponse fetchLeaveType() {
        List<KeyValue<Long, String>> dropDownLeaveType = this.leaveTypeRepo.fetchRequestType();
        return ApiResponse.success(java.util.Map.of("dropDownLeaveType", dropDownLeaveType), "leave types " +
                                                                                             "fetch  successfully "
        );
    }

    @Transactional
    public ApiResponse applyLeave(ApplyLeaveRequest applyLeaveRequest, Long loggedInUserId) {
        var leaveTypeEntity =
                leaveTypeRepo.findLeaveTypeById(applyLeaveRequest.leaveTypeId()).orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));
        LeaveRequestEntity leaveRequestEntity = prepareApplyRequest(applyLeaveRequest, leaveTypeEntity, loggedInUserId);
        LeaveRequestEntity savedLeaveRequest = this.leaveRequestRepo.save(leaveRequestEntity);
        return ApiResponse.success(Map.of("applyLeaveRequest", savedLeaveRequest.getId()), "leave request apply " +
                                                                                           "successfully");


    }

    private  LeaveRequestEntity prepareApplyRequest(ApplyLeaveRequest applyLeaveRequest,
                                                    LeaveTypeEntity leaveTypeEntity,Long loggedInUserId) {
        UserEntity userEntity = userRepo.findUserByIdAndDeleteFlagFalse(loggedInUserId).orElseThrow(() -> new ResourceNotFoundException(
                "user not found for this user Id"));
        checkLeaveApplyDublicate(loggedInUserId, applyLeaveRequest.startDate(), applyLeaveRequest.endDate());
        boolean isAvailableLeave = checkAvailableCountForLeaveType(loggedInUserId, leaveTypeEntity.getId());
        if(!isAvailableLeave){
            throw new LeaveNotAvailableException("leave is not available");
        }

        UserReportingDetailEntity userReportingDetailEntity = userReportingDetailRepo.findUserReportingDetailByUserId(userEntity.getId()).orElseThrow(() -> new ResourceNotFoundException("user reporting detail not found for userId"));
         log.info("userReortingDetail : {} ",  userReportingDetailEntity);
        LeaveRequestEntity leaveRequestEntity = new LeaveRequestEntity();
        leaveRequestEntity.setLeaveStatusEnum(LeaveStatusEnum.PENDING);
        leaveRequestEntity.setLeaveTypeEntity(leaveTypeEntity);
        leaveRequestEntity.setLeaveReason(applyLeaveRequest.leaveReason());
        leaveRequestEntity.setStartDate(applyLeaveRequest.startDate());
        leaveRequestEntity.setEndDate(applyLeaveRequest.endDate());
        leaveRequestEntity.setUser(userEntity);
        leaveRequestEntity.setCreatedBy(userEntity);
        leaveRequestEntity.setUpdatedBy(userEntity);
        leaveRequestEntity.setManager(userReportingDetailEntity.getReportingManager());
        return leaveRequestEntity;
    }


    private  boolean checkAvailableCountForLeaveType(Long userId, Long leaveTypeId){
        int availableCountOfLeaveType = userLeveCountRepo.findAvailableCountOfLeaveType(userId, leaveTypeId);
        log.info("checkAvailableCountForLeaveType : {}", availableCountOfLeaveType);
        return availableCountOfLeaveType > 0;
    }

  private void checkLeaveApplyDublicate(Long userId, Instant startDate , Instant endDate){
      int leaveApplyCountAlready = leaveRequestRepo.countOverlappingLeaves(userId, startDate, endDate);
      if(leaveApplyCountAlready > 0){
         throw new  HrmsException("already apply the leave between this date range", HttpStatus.BAD_REQUEST);
      }
  }




}
