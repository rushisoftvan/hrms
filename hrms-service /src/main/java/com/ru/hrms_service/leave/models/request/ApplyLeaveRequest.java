package com.ru.hrms_service.leave.models.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;

public record ApplyLeaveRequest(

        @NotNull(message = "leave type should not be null")
        Long leaveTypeId,

        @NotNull
        //@FutureOrPresent(message = "Start date must be in the present or future")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        Instant startDate,

        @NotNull
       // @FutureOrPresent(message = "End date must be in the present or future")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        Instant endDate,

        @NotBlank(message = "leave reason should not be null or empty")
        String leaveReason

) {
}
