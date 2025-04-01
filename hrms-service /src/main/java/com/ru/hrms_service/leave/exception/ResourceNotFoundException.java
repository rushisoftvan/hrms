package com.ru.hrms_service.leave.exception;

import com.ru.hrms_service.holiday.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
