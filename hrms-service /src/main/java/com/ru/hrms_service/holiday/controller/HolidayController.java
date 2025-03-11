package com.ru.hrms_service.holiday.controller;

import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.holiday.models.request.FetchHolidaysReq;
import com.ru.hrms_service.holiday.services.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/hrms/holiday")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class HolidayController {

     private final HolidayService holidayService;

    @PostMapping("/fetch")
    public ResponseEntity<ApiResponse> fetchHolidayList( @RequestBody FetchHolidaysReq fetchHolidaysReq){
       return ResponseEntity.ok(this.holidayService.fetchHolidays(fetchHolidaysReq));
    }

   @PostMapping("/importHolidayCsv")
   public ResponseEntity<ApiResponse> importHoliday(@RequestParam("file") MultipartFile file){
      return ResponseEntity.ok(this.holidayService.importHoliday(file));
   }


    @GetMapping("/getImportHolidayStatus/{batchId}")
    public ResponseEntity<ApiResponse> getHolidayImportStatus(@PathVariable("batchId") Long batchId) {
        return ResponseEntity.ok( this.holidayService.getHolidayImportStatus(batchId));
    }
}
