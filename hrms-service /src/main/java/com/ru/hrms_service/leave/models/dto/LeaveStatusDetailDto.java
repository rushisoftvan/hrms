package com.ru.hrms_service.leave.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LeaveStatusDetailDto {

       String name;
       String reporterName;
       String startDate;
       String endDate;
       String leaveStatus;

}
