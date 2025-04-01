package com.ru.hrms_service.leave.enums;

public enum LeaveStatusEnum {


    APPROVED("Approved"),

    REJECTED("Rejected"),

    PENDING("Pending"),

    CANCELLED("Cancelled");


    private String value;

    private LeaveStatusEnum(String value){
        this.value=value;
    }


    public String value(){
        return value;
    }



}
