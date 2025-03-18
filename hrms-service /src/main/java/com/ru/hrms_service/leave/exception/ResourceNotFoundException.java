package com.ru.hrms_service.leave.exception;

import com.ru.hrms_service.holiday.exception.HrmsException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends HrmsException {
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
