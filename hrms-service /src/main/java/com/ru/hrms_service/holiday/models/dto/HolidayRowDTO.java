package com.ru.hrms_service.holiday.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HolidayRowDTO {

   private String holidayName;

   private String holidayDate;

   private String isOptional;


}
