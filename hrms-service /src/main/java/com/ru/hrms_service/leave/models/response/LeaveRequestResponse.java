package com.ru.hrms_service.leave.models.response;

import com.ru.hrms_service.leave.enums.LeaveStatusEnum;
import com.ru.hrms_service.leave.enums.LeaveTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestResponse {

    private  String empName;

    private LeaveTypeEnum leaveType;

    private Instant startDate;

    private Instant endDate;

    private LeaveStatusEnum leaveStatusEnum;

    private String leaveReason;

    private Instant dateOfRequest;
}
