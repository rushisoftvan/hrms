package com.ru.hrms_service.leave.projection;

public interface LeaveCountDetailProjection {


    Long  getLeaveTypeId();

    String getLeaveTyp();

    double  availableCount();

    double bookedCount();



}
