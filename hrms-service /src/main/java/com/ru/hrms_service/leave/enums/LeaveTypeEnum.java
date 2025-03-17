package com.ru.hrms_service.leave.enums;

public enum LeaveTypeEnum {


    CASUAL_LEAVE("`Casual Leave`"),

    PRIVILEGE_LEAVE("Privilege Leave"),

    RESTRICTED_HOLIDAY("Restricted Holiday"),

    SICK_LEAVE("Sick leave");


    private String value;

    private LeaveTypeEnum(String value){
        this.value=value;
    }


    public String value(){
        return value;
    }


}
