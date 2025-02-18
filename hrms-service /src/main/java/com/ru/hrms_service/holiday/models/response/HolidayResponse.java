package com.ru.hrms_service.holiday.models.response;

import com.ru.hrms_service.holiday.entities.HolidayEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class HolidayResponse {

    private String holidayName;
    private LocalDate holidayDate;
    private Boolean isOptional;
    private String holidayDay;


    private static String convertDateToDay(LocalDate holidayDate){
        return   holidayDate.getDayOfWeek().toString();
    }

    public static   HolidayResponse  prepareHolidayResponse(HolidayEntity holiday){
       return builder()
                .holidayDate(holiday.getHolidayDate())
                .holidayDay(convertDateToDay(holiday.getHolidayDate()))
                .isOptional(holiday.isOptionl())
                .holidayName(holiday.getName())
                .build();

    }

}
