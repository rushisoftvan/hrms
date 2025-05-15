package com.ru.hrms_service.common.models.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse   {

    private int statusCode;
    private String message;
    private Object data;
    private Object errorMessages;
    private boolean success;

    @JsonIgnore
    private HttpStatus httpStatus;


    public ApiResponse(HttpStatus statusCode, String message) {
        this.statusCode = statusCode.value();
        this.message = message;
    }

    public static ApiResponse success(Object data, String message) {
        return builder()
                .data(data)
                .statusCode(HttpStatus.OK.value())
                .message(message).build();
    }

    public static ApiResponse fail(Object data,String message, HttpStatus httpStatus) {
        return builder()
                .data(data)
                .message(message)
                .statusCode(httpStatus.value())
                .success(false)
                .build();
    }

    public static ApiResponse failWithHttpCdde(Object data,Object message, int statusCode) {
        return builder()
                .data(data)
                .errorMessages(message)
                .statusCode(statusCode)
                .success(false)
                .build();
    }

    public static ApiResponse processing(Object value, HttpStatus httpStatus) {
        return builder()
                .data(value)
                .message("Your import process has started. You will be notified once it's completed, and you can check by batchId.")
                .statusCode(httpStatus.value())
                .build();
    }


}
