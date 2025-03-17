package com.ru.hrms_service.holiday.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportHolidayResponse {

    private  Long  batchId;

    private String checkStatusUrl;

}
