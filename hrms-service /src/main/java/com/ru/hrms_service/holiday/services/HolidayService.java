package com.ru.hrms_service.holiday.services;

import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.holiday.entities.HolidayEntity;
import com.ru.hrms_service.holiday.models.request.FetchHolidaysReq;
import com.ru.hrms_service.holiday.models.response.HolidayResponse;
import com.ru.hrms_service.holiday.repositories.HolidayRepo;
import com.ru.hrms_service.holiday.specification.HolidaySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HolidayService {
    private final HolidayRepo holidayRepo;

    public ApiResponse fetchHolidays(FetchHolidaysReq fetchHolidaysReq) {
        var holidaysSpec = HolidaySpecification.fetchHolidays(fetchHolidaysReq);
        List<HolidayResponse> listOfHolidayResponse = prepareHolidayResponse(holidaysSpec);
        log.info("holiday response : {}",listOfHolidayResponse.size());
        return ApiResponse.success(listOfHolidayResponse, "fetched successfully");
    }

    private List<HolidayResponse> prepareHolidayResponse(Specification<HolidayEntity> holidaysSpec) {
        List<HolidayEntity> holidays = holidayRepo.findAll(holidaysSpec);
        log.info("holiday size : {}", holidays.size());
        return holidays.stream()
                .map(HolidayResponse::prepareHolidayResponse)
                .toList();
    }
}
