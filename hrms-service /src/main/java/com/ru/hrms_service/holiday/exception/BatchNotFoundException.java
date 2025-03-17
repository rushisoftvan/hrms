package com.ru.hrms_service.holiday.exception;

import org.springframework.http.HttpStatus;

public class BatchNotFoundException extends HrmsException {
    public BatchNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
