package com.ru.hrms_service.holiday.enums;

public enum ImportHolidayStatusEnum {

    NEW("NEW"),

    INVALID("INVALID"),

    INSERTED("INSERTED"),

    DUPLICATE("DUBLICATE"),

    PROGRESS("INPROGRESS");


    private String value;


    private ImportHolidayStatusEnum (String value){
        this.value=value;
    }

}
