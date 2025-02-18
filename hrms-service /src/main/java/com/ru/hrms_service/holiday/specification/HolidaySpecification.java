package com.ru.hrms_service.holiday.specification;

import com.ru.hrms_service.holiday.entities.HolidayEntity;
import com.ru.hrms_service.holiday.enums.HolidayStatusCodeEnum;
import com.ru.hrms_service.holiday.models.request.FetchHolidaysReq;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class HolidaySpecification {

         public  static Specification<HolidayEntity> fetchHolidays(FetchHolidaysReq fetchHolidaysReq){

             return (root, query, criteriaBuilder) -> {
                 List<Predicate> predicates = buildFetchPredicates(fetchHolidaysReq, root, criteriaBuilder);
                 return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
             };

         }

         private HolidaySpecification(){
              throw new IllegalArgumentException();
         }

    private static List<Predicate> buildFetchPredicates(FetchHolidaysReq fetchHolidaysReq,
                                                        Root<HolidayEntity> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(fetchHolidaysReq.searchText())){
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + fetchHolidaysReq.searchText() + "%"));
        }
        LocalDate currentDate = LocalDate.now();
        if(HolidayStatusCodeEnum.UPC == fetchHolidaysReq.holidayStatusCode()){
               predicates.add(criteriaBuilder.greaterThan(root.get("holidayDate"),currentDate));
        }
        if(HolidayStatusCodeEnum.HG==fetchHolidaysReq.holidayStatusCode()){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("holidayDate"),currentDate));
        }
        predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
        return predicates;
    }
}
