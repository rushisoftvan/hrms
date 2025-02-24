package com.ru.hrms_service.holiday.services.mocks;

import com.ru.hrms_service.holiday.entities.HolidayEntity;
import com.ru.hrms_service.holiday.models.response.HolidayResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HolidayTestMock {

     public HolidayResponse setHolidayResponse(){
         HolidayResponse holidayResponse = new HolidayResponse();
         holidayResponse.setHolidayName("Mahashivratri");
         holidayResponse.setIsOptional(true);
         holidayResponse.setHolidayDay("wed");
         return holidayResponse;
     }

    public HolidayEntity setHolidayEntity(){
        HolidayEntity holidayEntity = new HolidayEntity();
        holidayEntity.setName("Mahashivratri");
        holidayEntity.setOptionl(true);
        holidayEntity.setId(1L);

        holidayEntity.setHolidayDate(LocalDate.now());
        return holidayEntity;
 }


    public List<HolidayEntity> setHolidayEntityList() {
        List<HolidayEntity> holidayEntities = new ArrayList<>();
        HolidayEntity holidayEntity = setHolidayEntity();
        holidayEntities.add(holidayEntity);
        return holidayEntities;
    }


}
