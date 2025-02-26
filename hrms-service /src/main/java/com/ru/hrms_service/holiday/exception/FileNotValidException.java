package com.ru.hrms_service.holiday.exception;

import org.springframework.http.HttpStatus;

public class FileNotValidException extends HrmsException{
    public FileNotValidException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
