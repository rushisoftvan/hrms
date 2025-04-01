package com.ru.hrms_service.holiday.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private int statusCode;

    public CustomException(String message, HttpStatus httpStatus)
    {
        super(message);
        this.statusCode = httpStatus.value();
    }
}
