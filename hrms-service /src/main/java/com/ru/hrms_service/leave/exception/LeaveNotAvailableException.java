package com.ru.hrms_service.leave.exception;

import com.ru.hrms_service.holiday.exception.HrmsException;
import org.springframework.http.HttpStatus;

public class LeaveNotAvailableException  extends HrmsException {

    public LeaveNotAvailableException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
