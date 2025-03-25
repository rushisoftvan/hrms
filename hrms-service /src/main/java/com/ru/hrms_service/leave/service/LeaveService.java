package com.ru.hrms_service.leave.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ru.hrms_service.common.entities.UserEntity;
import com.ru.hrms_service.common.models.MailDto;
import com.ru.hrms_service.common.models.PagedResult;
import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.common.projection.KeyValue;
import com.ru.hrms_service.common.repositories.UserRepo;
import com.ru.hrms_service.common.sender.EmailSenderService;
import com.ru.hrms_service.holiday.exception.CustomException;
import com.ru.hrms_service.leave.entities.LeaveRequestEntity;
import com.ru.hrms_service.leave.entities.LeaveTypeEntity;
import com.ru.hrms_service.leave.entities.UserReportingDetailEntity;
import com.ru.hrms_service.leave.enums.LeaveStatusEnum;
import com.ru.hrms_service.leave.enums.LeaveTypeEnum;
import com.ru.hrms_service.leave.exception.ResourceNotFoundException;
import com.ru.hrms_service.leave.models.dto.LeaveStatusDetailDto;
import com.ru.hrms_service.leave.models.request.ApplyLeaveRequest;
import com.ru.hrms_service.leave.models.request.FetchLeaveRequest;
import com.ru.hrms_service.leave.models.request.UpdateLeaveStatusRequest;
import com.ru.hrms_service.leave.models.response.LeaveRequestResponse;
import com.ru.hrms_service.leave.repositories.LeaveRequestRepo;
import com.ru.hrms_service.leave.repositories.LeaveTypeRepo;
import com.ru.hrms_service.leave.repositories.UserLeveCountRepo;
import com.ru.hrms_service.leave.repositories.UserReportingDetailRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private final EmailSenderService  emailSenderService;

    private final ObjectMapper objectMapper;


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

    private LeaveRequestEntity prepareApplyRequest(ApplyLeaveRequest applyLeaveRequest,
                                                   LeaveTypeEntity leaveTypeEntity, Long loggedInUserId) {
        UserEntity userEntity = userRepo.findUserByIdAndDeleteFlagFalse(loggedInUserId).orElseThrow(() -> new ResourceNotFoundException(
                "user not found for this user Id"));
        checkLeaveApplyDuplicate(loggedInUserId, applyLeaveRequest.startDate(), applyLeaveRequest.endDate());
           if(leaveTypeEntity.getType() != LeaveTypeEnum.LEAVE_WITHOUT_PAY){
               boolean isAvailableLeave = checkAvailableCountForLeaveType(loggedInUserId, leaveTypeEntity.getId());
               if (!isAvailableLeave) {
                   throw new CustomException("leave is not available",HttpStatus.BAD_REQUEST);
               }
           }


        UserReportingDetailEntity userReportingDetailEntity = userReportingDetailRepo.findUserReportingDetailByUserId(userEntity.getId()).orElseThrow(() -> new ResourceNotFoundException("user reporting detail not found for userId"));
        log.info("userReortingDetail : {} ", userReportingDetailEntity);
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

    private boolean checkAvailableCountForLeaveType(Long userId, Long leaveTypeId) {
        int availableCountOfLeaveType = userLeveCountRepo.findAvailableCountOfLeaveType(userId, leaveTypeId);
        log.info("checkAvailableCountForLeaveType : {}", availableCountOfLeaveType);
        return availableCountOfLeaveType > 0;
    }

    private void checkLeaveApplyDuplicate(Long userId, Instant startDate, Instant endDate) {
        int leaveApplyCountAlready = leaveRequestRepo.countOverlappingLeaves(userId, startDate, endDate);
        log.info("checkLeaveApplyDuplicate :: leaveApplyCountAlready : {} ", leaveApplyCountAlready);
        if (leaveApplyCountAlready > 0) {
            throw new CustomException("already apply the leave between this date range", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void updateLeaveStatus(UpdateLeaveStatusRequest updateLeaveStatusRequest, Long userId) {



        //getLeaveRequest
        LeaveRequestEntity leaveRequestEntity =
                leaveRequestRepo.findLLeaveRequest(updateLeaveStatusRequest.leaveRequestId()).orElseThrow(() -> new ResourceNotFoundException("leave " +
                                                                                                                                           "request not " + "found"));

        if(updateLeaveStatusRequest.leaveStatus() == LeaveStatusEnum.CANCELLED){
            if(leaveRequestEntity.getUser().getId() == userId){
                leaveRequestEntity.setLeaveStatusEnum(updateLeaveStatusRequest.leaveStatus());
                leaveRequestEntity.setUpdatedBy(new UserEntity(userId));
                leaveRequestEntity.setUpdatedOn(Instant.now());
                LeaveRequestEntity updatedLeaveRequest = leaveRequestRepo.save(leaveRequestEntity);

            }
            else{

                throw new CustomException("you can not change the status", HttpStatus.BAD_REQUEST)  ;
            }
        }
        else{
            leaveRequestEntity.setLeaveStatusEnum(updateLeaveStatusRequest.leaveStatus());
            leaveRequestEntity.setUpdatedBy(new UserEntity(userId));
            leaveRequestEntity.setUpdatedOn(Instant.now());
            LeaveRequestEntity updatedLeaveRequest = leaveRequestRepo.save(leaveRequestEntity);

            if(updatedLeaveRequest.getLeaveStatusEnum() == LeaveStatusEnum.APPROVED || updatedLeaveRequest.getLeaveStatusEnum() == LeaveStatusEnum.REJECTED ){
                LeaveStatusDetailDto leaveStatusDetailDto = new LeaveStatusDetailDto(
                        updatedLeaveRequest.getUser().getFirstName() + " " + updatedLeaveRequest.getUser().getLastName(),
                        updatedLeaveRequest.getManager().getFirstName() + " " + updatedLeaveRequest.getManager().getLastName(), updatedLeaveRequest.getStartDate().toString(),
                        updatedLeaveRequest.getEndDate().toString(), updatedLeaveRequest.getLeaveStatusEnum().value());
                sendLeaveStatusDetails(leaveStatusDetailDto, updatedLeaveRequest.getUser().getEmail());

            }

        }

    }

    private void sendLeaveStatusDetails( LeaveStatusDetailDto leaveStatusDetailDto, String email) {
        Map<String, Object> templateVariableNameToValueMap = Map.of(
                "employeeName", leaveStatusDetailDto.getName(),
                "startDate", leaveStatusDetailDto.getStartDate(),
                "endDate", leaveStatusDetailDto.getEndDate(),
                "managerName", leaveStatusDetailDto.getReporterName(),
                "status" , leaveStatusDetailDto.getLeaveStatus()

        );
        MailDto mailDTO = new MailDto("Leave Details and Status Update",
                email, templateVariableNameToValueMap,
                "leave_request_email");
        emailSenderService.sendNotification(mailDTO);
    }

    public ApiResponse fetchLeaves(FetchLeaveRequest fetchLeaveRequest, Long userId)  {
        log.info("fetch request detail : {}", fetchLeaveRequest);

        Pageable pageable = fetchLeaveRequest.preparePageRequest();

        Page<LeaveRequestEntity> leaveRequestEntities = this.leaveRequestRepo.fetchLeaves(fetchLeaveRequest.getLeaveTypeId(),
                fetchLeaveRequest.getLeaveStatus(), fetchLeaveRequest.getStartDate(),
                fetchLeaveRequest.getEndDate(), pageable);
        List<LeaveRequestResponse> leaveRequestResponses = prepareLeaveRequestsResponse(leaveRequestEntities);
        var leaveRequestResponsePagedResult = PagedResult.preparePagedResponse(leaveRequestEntities,
                leaveRequestResponses);
       return ApiResponse.success(leaveRequestResponsePagedResult,"leave requests fetch successfully");
    }

    private static List<LeaveRequestResponse> prepareLeaveRequestsResponse(Page<LeaveRequestEntity> leaveRequestEntities) {
        List<LeaveRequestEntity> content = leaveRequestEntities.getContent();

        return content.stream().map(entity ->
                new LeaveRequestResponse(
                        String.join(" ", entity.getUser().getFirstName(), entity.getUser().getLastName()), // Assuming getName()
                        // exists in
                        // UserEntity
                        entity.getLeaveTypeEntity().getType(), // Assuming getLeaveType() exists in LeaveTypeEntity
                        entity.getStartDate(),
                        entity.getEndDate(),
                        entity.getLeaveStatusEnum(),
                        entity.getLeaveReason()
                )
        ).collect(Collectors.toList());
    }
}
