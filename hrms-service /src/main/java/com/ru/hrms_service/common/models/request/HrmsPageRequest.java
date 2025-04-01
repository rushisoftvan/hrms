package com.ru.hrms_service.common.models.request;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public abstract class HrmsPageRequest {

    private final int pageNo;
    private final Sort sort;
    private final int pageSize = 2;

    protected HrmsPageRequest(int pageNo, String sortField, String sortOrder) {
        this.pageNo = pageNo;
        this.sort = createSort(sortField, sortOrder);  // Calls the correctly defined method below
    }

    public Pageable preparePageRequest() {
        int pageNo = getPageNo() <= 1 ? 0 : getPageNo() - 1;
        return PageRequest.of(pageNo, pageSize, getSort());
    }

    private Sort createSort(String sortField, String sortOrder) {
        if (sortField == null || sortOrder == null) {
            return Sort.unsorted();
        }
        return sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
    }
}
