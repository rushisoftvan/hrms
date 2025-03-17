package com.ru.hrms_service.holiday.enums;

public enum BatchJobStatusEnum {

    IN_PROGRESS("INPROGRESS"),

    COMPLETED("COMPLETED"),

    ERROR("ERROR");

    private String value;

    private BatchJobStatusEnum(String value){
        this.value=value;
    }


    public String value(){
        return value;
    }


}
