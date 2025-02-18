package com.ru.hrms_service.holiday.controller;

import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.holiday.models.request.FetchHolidaysReq;
import com.ru.hrms_service.holiday.services.HolidayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hrms/holiday")
@RequiredArgsConstructor
public class HolidayController {

     private final HolidayService holidayService;

    @PostMapping("/fetch")
    public ResponseEntity<ApiResponse> fetchHolidayList(@Valid @RequestBody FetchHolidaysReq fetchHolidaysReq){
       return ResponseEntity.ok(this.holidayService.fetchHolidays(fetchHolidaysReq));
       
    }

}
