package com.ru.hrms_service.holiday.exception;

import org.springframework.http.HttpStatus;

public class HrmsException extends RuntimeException {

    private int statusCode;

    public HrmsException(String message, HttpStatus httpStatus)
    {
        super(message);
        this.statusCode = httpStatus.value();
    }
}
