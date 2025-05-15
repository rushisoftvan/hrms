package com.ru.hrms_service.holiday.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private int statusCode;

    public CustomException(String message, HttpStatus httpStatus)
    {
        super(message);
        this.statusCode = httpStatus.value();
    }
}
