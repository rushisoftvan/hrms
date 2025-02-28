package com.ru.hrms_service.holiday.controller;

import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.holiday.models.request.FetchHolidaysReq;
import com.ru.hrms_service.holiday.services.HolidayService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/hrms/holiday")
@RequiredArgsConstructor
public class HolidayController {

     private final HolidayService holidayService;

    @PostMapping("/fetch")
    public ResponseEntity<ApiResponse> fetchHolidayList( @RequestBody FetchHolidaysReq fetchHolidaysReq){
       return ResponseEntity.ok(this.holidayService.fetchHolidays(fetchHolidaysReq));
    }

   @PostMapping("/importHolidayCsv")
   public void importHoliday(@RequestParam("file") MultipartFile file){
        this.holidayService.importHoliday(file);
   }


   @GetMapping("/getImportHolidayStatus/{bacthId}")
   public void  getHolidayImportStatus(@PathVariable("batcgId") Long batchId){
        this.holidayService.getHolidayImportStatus(batchId);
   }

}
