package com.ru.hrms_service.common.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

@Builder
public record PagedResult<T>(
        List<T> content,
        long totalElements,
        int pageNumber,
        int totalPages,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious
) {



    private Sort createSort(String sortField, String sortOrder) {
        if (sortField == null || sortOrder == null) {
            return Sort.unsorted();
        }
        return sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
    }



    public static <P, R> PagedResult<R> preparePagedResponse(Page<P> pagedEntity, List<R> dataResponseList) {
        return PagedResult.<R>builder()
                .content(dataResponseList)
                .totalElements(pagedEntity.getTotalElements())
                .pageNumber(pagedEntity.getNumber() + 1)
                .totalPages(pagedEntity.getTotalPages())
                .hasPrevious(pagedEntity.hasPrevious())
                .hasNext(pagedEntity.hasNext())
                .isFirst(pagedEntity.isFirst())
                .isLast(pagedEntity.isLast())
                .build();
    }

}
