package com.ru.hrms_service.leave.models.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record ApplyLeaveRequest(

        @NotNull(message = "leave type should not be null")
        Long leaveTypeId,

        @NotNull
        @FutureOrPresent(message = "Start date must be in the present or future")
        Instant startDate,

        @NotNull
        @FutureOrPresent(message = "End date must be in the present or future")
        Instant endDate,

        @NotEmpty(message = "leave reason should not be null or empty")
        String leaveReason

) {
}
