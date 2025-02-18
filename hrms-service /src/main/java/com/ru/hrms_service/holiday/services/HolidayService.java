package com.ru.hrms_service.holiday.services;

import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.holiday.entities.HolidayEntity;
import com.ru.hrms_service.holiday.models.request.FetchHolidaysReq;
import com.ru.hrms_service.holiday.models.response.HolidayResponse;
import com.ru.hrms_service.holiday.repositories.HolidayRepo;
import com.ru.hrms_service.holiday.specification.HolidaySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayService {


   private  final HolidayRepo holidayRepo;


    public ApiResponse fetchHolidays(FetchHolidaysReq fetchHolidaysReq){
        var holidaysSpec = HolidaySpecification.fetchHolidays(fetchHolidaysReq);
        List<HolidayEntity>  holidays = holidayRepo.findAll(holidaysSpec);

        List<HolidayResponse> listOfHolidayResponse = holidays.stream()
                .map(HolidayResponse::prepareHolidayResponse)  // Transform each holiday
                .toList();// Collect the transformed results into a new list
        return ApiResponse.success(listOfHolidayResponse,"fetched successfully");
    }

}
