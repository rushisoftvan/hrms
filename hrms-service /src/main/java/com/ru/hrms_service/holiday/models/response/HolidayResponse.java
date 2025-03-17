package com.ru.hrms_service.holiday.models.response;

import com.ru.hrms_service.holiday.entities.HolidayEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
                .isOptional(holiday.isOptional())
                .holidayName(holiday.getName())
                .build();

    }

}
