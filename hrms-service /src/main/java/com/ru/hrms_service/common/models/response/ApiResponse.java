package com.ru.hrms_service.common.models.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse   {

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

    public static ApiResponse fail(Object data,String message, HttpStatus httpStatus) {
        return builder()
                .data(data)
                .message(message)
                .status(httpStatus.value())
                .build();
    }

    public static ApiResponse processing(Object value, HttpStatus httpStatus) {
        return builder()
                .data(value)
                .message("Your import process has started. You will be notified once it's completed, and you can check by batchId.")
                .status(httpStatus.value())
                .build();
    }


}
