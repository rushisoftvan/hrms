package com.ru.hrms_service.common.models.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
public class ApiResponse {

    private int status;
    private String message;
    private Object data;

    @JsonIgnore
    private HttpStatus httpStatus;


    public ApiResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    public static ApiResponse success(Object data, String message) {
        return builder()
                .data(data)
                .status(HttpStatus.OK.value())
                .message(message).build();
    }

    public static ApiResponse fail(String message, HttpStatus httpStatus) {
        return builder()
                .message(message)
                .status(httpStatus.value())
                .build();
    }
}
