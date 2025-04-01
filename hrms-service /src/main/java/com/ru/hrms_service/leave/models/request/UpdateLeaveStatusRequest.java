package com.ru.hrms_service.leave.models.request;

import com.ru.hrms_service.leave.enums.LeaveStatusEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateLeaveStatusRequest(
        @NotNull(message = "leave request id missing")
        Long leaveRequestId,


        @Pattern(regexp = "APPROVED|REJECTED|PENDING", message = "Invalid leave status")
        LeaveStatusEnum leaveStatus) {
}
