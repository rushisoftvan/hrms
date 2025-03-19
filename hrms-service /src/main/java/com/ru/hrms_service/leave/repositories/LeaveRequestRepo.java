package com.ru.hrms_service.leave.repositories;

import com.ru.hrms_service.leave.entities.LeaveRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequestEntity, Long> {


    @Query(value = """
        SELECT COUNT(1) FROM  leave_request as l
        WHERE user_id = :userId 
        AND  (
            (start_date <= :endDate AND end_date >= :startDate) 
        )
    """, nativeQuery = true)
    int countOverlappingLeaves(@Param("userId") Long userId,
                               @Param("startDate") Instant startDate,
                               @Param("endDate") Instant endDate);

}
