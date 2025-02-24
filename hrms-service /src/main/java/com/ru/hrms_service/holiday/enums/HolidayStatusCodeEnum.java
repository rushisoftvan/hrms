    package com.ru.hrms_service.holiday.enums;

    public enum HolidayStatusCodeEnum {
        UPC("Upcoming"),
        HG("Has Gone");

        private  final  String title;

        private HolidayStatusCodeEnum(String title){
            this.title=title;
        }

    }
