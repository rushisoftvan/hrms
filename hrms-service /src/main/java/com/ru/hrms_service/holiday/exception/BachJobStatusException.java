package com.ru.hrms_service.holiday.exception;

import org.springframework.http.HttpStatus;

public class BachJobStatusException extends CustomException {
    public BachJobStatusException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
