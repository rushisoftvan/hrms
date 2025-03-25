package com.ru.hrms_service.leave.models.request;

import com.ru.hrms_service.common.models.request.HrmsPageRequest;
import com.ru.hrms_service.leave.enums.LeaveStatusEnum;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

import java.time.Instant;

@Getter
@ToString
public class FetchLeaveRequest extends HrmsPageRequest {

    public static final String DEFAULT_SORT_BY = "updatedOn";

    private final String searchText;
    private final Long leaveTypeId;
    private final LeaveStatusEnum leaveStatus;
    private final Instant startDate;
    private final Instant endDate; // Fixed camel case


    public FetchLeaveRequest(String searchText, int pageNo, String sortField, String sortOrder,
                             LeaveStatusEnum leaveStatusEnum, Instant startDate, Instant endDate,
                             Long leaveTypeId) {
        super(pageNo, sortField, sortOrder );
        this.searchText = searchText;
        this.leaveStatus = leaveStatusEnum;
        this.startDate = startDate;
        this.endDate = endDate; // Fixed field name
        this.leaveTypeId = leaveTypeId;

    }

    private static Sort getSort(String sortField, String sortOrder) {
        if (sortField == null || sortField.isEmpty()) {
            sortField = DEFAULT_SORT_BY; // Use default sorting field if none is provided
        }
        return "desc".equalsIgnoreCase(sortOrder) ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
    }
}
