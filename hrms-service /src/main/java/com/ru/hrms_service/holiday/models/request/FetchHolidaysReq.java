package com.ru.hrms_service.holiday.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ru.hrms_service.holiday.enums.HolidayStatusCodeEnum;

public record FetchHolidaysReq(
   @JsonProperty("holidayStatus")
  HolidayStatusCodeEnum holidayStatusCode,
   String searchText
) {
}
